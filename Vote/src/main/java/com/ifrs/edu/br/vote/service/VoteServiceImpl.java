package com.ifrs.edu.br.vote.service;

import com.ifrs.edu.br.vote.model.Vote;
import com.ifrs.edu.br.vote.queue.QueueSender;
import com.ifrs.edu.br.vote.repository.VoteRepository;
import com.ifrs.edu.br.vote.util.dto.EmailNotifyDTO;
import com.ifrs.edu.br.vote.util.dto.VoteDTO;
import com.ifrs.edu.br.vote.util.response.PollResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final QueueSender queueSender;

    public VoteServiceImpl(VoteRepository voteRepository, QueueSender queueSender) {
        this.voteRepository = voteRepository;
        this.queueSender = queueSender;
    }

    @CacheEvict(value = "result", key = "#voteToCompute.identifier")
    @RabbitListener(queues = {"${queue.vote.name}"})
    public void voteOnPoll(VoteDTO voteToCompute) {
        log.debug("voteOnPoll - start()");

        Vote newVote = new Vote();

        newVote.setIdpoll(voteToCompute.identifier());
        newVote.setIdoption(voteToCompute.option());

        voteRepository.save(newVote);

        queueSender.sendEmailNotification(new EmailNotifyDTO(voteToCompute.identifier(), voteToCompute.email()));
    }

    @Override
    @Cacheable(value = "result")
    public PollResponse getResult(UUID id) {
        return new PollResponse(voteRepository.getVoteCountByPoll(id));
    }
}
