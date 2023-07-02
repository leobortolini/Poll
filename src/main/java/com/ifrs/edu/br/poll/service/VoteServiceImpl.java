package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Option;
import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.model.Vote;
import com.ifrs.edu.br.poll.repository.VoteRepository;
import com.ifrs.edu.br.poll.util.dto.VoteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteServiceImpl.class);
    private final VoteRepository voteRepository;
    private final PollService pollService;

    public VoteServiceImpl(VoteRepository voteRepository, PollService pollService) {
        this.voteRepository = voteRepository;
        this.pollService = pollService;
    }

    @CacheEvict(value = "result", key = "#vote.identifier")
    @RabbitListener(queues = {"${queue.name}"})
    public void voteOnPoll(VoteDTO vote) {
        LOGGER.debug("voteOnPoll - start()");
        Optional<Poll> poll = pollService.findByIdentifier(vote.getIdentifier());

        if (poll.isPresent()) {
            Optional<Option> option = poll.get().getOptions().stream().filter(opt -> Objects.equals(opt.getId(), vote.getVoteRequest().getOptionId())).findFirst();

            if (option.isPresent()) {
                Vote newVote = new Vote();

                newVote.setPoll(poll.get());
                newVote.setOption(option.get());

                voteRepository.save(newVote);
            }
        }
    }

    @Override
    public Optional<Poll> findPollByIdentifier(UUID identifier) {
        return pollService.findByIdentifier(identifier);
    }
}
