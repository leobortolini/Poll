package com.ifrs.edu.br.vote.service;

import com.ifrs.edu.br.vote.model.Vote;
import com.ifrs.edu.br.vote.queue.QueueSender;
import com.ifrs.edu.br.vote.repository.PollResponseRepository;
import com.ifrs.edu.br.vote.repository.VoteRepository;
import com.ifrs.edu.br.vote.util.dto.EmailNotifyDTO;
import com.ifrs.edu.br.vote.util.dto.VoteDTO;
import com.ifrs.edu.br.vote.util.response.PollResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final PollResponseRepository pollResponseRepository;
    private final QueueSender queueSender;

    public VoteServiceImpl(VoteRepository voteRepository, QueueSender queueSender, PollResponseRepository pollResponseRepository) {
        this.voteRepository = voteRepository;
        this.pollResponseRepository = pollResponseRepository;
        this.queueSender = queueSender;
    }

    @RabbitListener(queues = {"${queue.vote.name}"}, concurrency = "5")
    public void voteOnPoll(List<VoteDTO> votesToCompute) {
        log.info("voteOnPoll - start() with size " + votesToCompute.size());
        pollResponseRepository.deleteAllById(votesToCompute.stream().map(VoteDTO::identifier).map(UUID::toString).toList());

        List<Vote> votes = new LinkedList<>();

        for (VoteDTO voteDTO : votesToCompute) {
            Vote newVote = new Vote();

            newVote.setIdpoll(voteDTO.identifier());
            newVote.setIdoption(voteDTO.option());

            votes.add(newVote);
        }
        List<Vote> computedVote = voteRepository.saveAllAndFlush(votes);

        if (computedVote.size() == votesToCompute.size()) {
            List<EmailNotifyDTO> notifications = new LinkedList<>();

            for (VoteDTO vote : votesToCompute) {
                notifications.add(new EmailNotifyDTO(vote.identifier(), vote.email()));
            }

            queueSender.sendEmailNotification(notifications);
        } else {
            voteRepository.flush();
            voteRepository.deleteAllInBatch(computedVote);
        }
    }

    @Override
    public PollResponse getResult(UUID id) {
        Optional<PollResponse> response = pollResponseRepository.findById(id.toString());

        if (response.isPresent()) {
            return response.get();
        }

        PollResponse newPollResponse = new PollResponse(id.toString(), voteRepository.getVoteCountByPoll(id));

        pollResponseRepository.save(newPollResponse);

        return newPollResponse;
    }
}
