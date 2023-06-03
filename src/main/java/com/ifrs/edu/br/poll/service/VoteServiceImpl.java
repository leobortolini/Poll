package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Option;
import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.model.Vote;
import com.ifrs.edu.br.poll.repository.PollRepository;
import com.ifrs.edu.br.poll.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService {
    private final PollRepository pollRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(PollRepository pollRepository, VoteRepository voteRepository) {
        this.pollRepository = pollRepository;
        this.voteRepository = voteRepository;
    }
    @Override
    @Cacheable(value = "poll")
    public Optional<Poll> findByIdentifier(UUID identifier) {
        return pollRepository.findByUuid(identifier);
    }

    @Override
    public Optional<Vote> voteOnPoll(UUID pollIdentifier, Long optionId) {
        Optional<Poll> poll = findByIdentifier(pollIdentifier);

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
