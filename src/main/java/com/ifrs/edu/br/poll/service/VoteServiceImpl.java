package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Option;
import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.model.Vote;
import com.ifrs.edu.br.poll.repository.VoteRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final PollService pollService;

    public VoteServiceImpl(VoteRepository voteRepository, PollService pollService) {
        this.voteRepository = voteRepository;
        this.pollService = pollService;
    }

    @Override
    @CacheEvict(value = "result", key = "#pollIdentifier")
    public Optional<Vote> voteOnPoll(UUID pollIdentifier, Long optionId) {
        Optional<Poll> poll = pollService.findByIdentifier(pollIdentifier);

        if (poll.isPresent()) {
            Optional<Option> option = poll.get().getOptions().stream().filter(opt -> Objects.equals(opt.getId(), optionId)).findFirst();

            if (option.isPresent()) {
                Vote newVote = new Vote();

                newVote.setPoll(poll.get());
                newVote.setOption(option.get());

                return Optional.of(voteRepository.save(newVote));
            }
        }

        return Optional.empty();
    }
}
