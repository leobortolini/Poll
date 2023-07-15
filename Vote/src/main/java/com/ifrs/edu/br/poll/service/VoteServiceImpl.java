package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Option;
import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.model.Vote;
import com.ifrs.edu.br.poll.queue.QueueSender;
import com.ifrs.edu.br.poll.repository.VoteRepository;
import com.ifrs.edu.br.poll.util.dto.EmailNotifyDTO;
import com.ifrs.edu.br.poll.util.dto.VoteDTO;
import com.ifrs.edu.br.poll.util.request.VoteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        Option boilerOption = new Option();
        Poll boilerPoll = new Poll();

        boilerPoll.setId(voteToCompute.getIdentifier());
        boilerOption.setId(voteToCompute.getVoteRequest().getOptionId());
        newVote.setPoll(boilerPoll);
        newVote.setOption(boilerOption);

        voteRepository.save(newVote);

        queueSender.sendEmailNotification(new EmailNotifyDTO(voteToCompute.getIdentifier(), voteToCompute.getVoteRequest().getEmail()));
    }

    @Override
    public void sendVote(UUID identifier, VoteRequest vote) {
        VoteDTO newVote = new VoteDTO(identifier, vote);

        queueSender.sendVote(newVote);
    }
}
