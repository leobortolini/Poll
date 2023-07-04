package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Option;
import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.model.Vote;
import com.ifrs.edu.br.poll.repository.VoteRepository;
import com.ifrs.edu.br.poll.util.dto.VoteDTO;
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
    private final PollService pollService;

    public VoteServiceImpl(VoteRepository voteRepository, PollService pollService) {
        this.voteRepository = voteRepository;
        this.pollService = pollService;
    }

    @CacheEvict(value = "result", key = "#vote.identifier")
    @RabbitListener(queues = {"${queue.name}"})
    public void voteOnPoll(VoteDTO vote) {
        log.debug("voteOnPoll - start()");
        Optional<Poll> poll = pollService.findByIdentifier(vote.getIdentifier());

        if (poll.isPresent()) {
            Vote newVote = new Vote();
            Option boilerOption = new Option();

            boilerOption.setId(vote.getVoteRequest().getOptionId());
            newVote.setPoll(poll.get());
            newVote.setOption(boilerOption);

            voteRepository.save(newVote);
        }
    }

    @Override
    public Optional<Poll> findPollByIdentifier(UUID identifier) {
        return pollService.findByIdentifier(identifier);
    }
}
