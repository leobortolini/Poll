package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Option;
import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.repository.PollRepository;
import com.ifrs.edu.br.poll.util.request.PollRequest;
import com.ifrs.edu.br.poll.util.response.PollResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;

    public PollServiceImpl(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Override
    public Poll save(PollRequest vote) {
        Poll newPoll = new Poll();

        newPoll.setTitle(vote.getTitle());
        newPoll.setDescription(vote.getDescription());

        List<Option> options = vote.getOptions().stream().map(option -> {
            Option newOption = new Option();

            newOption.setTitle(option);
            newOption.setPoll(newPoll);

            return newOption;
        }).toList();

        newPoll.setOptions(options);

        return pollRepository.save(newPoll);
    }

    @Override
    @Cacheable(value = "poll")
    public Optional<Poll> findByIdentifier(UUID id) {
        return pollRepository.findById(id);
    }

    @Override
    @Cacheable(value = "result")
    public Optional<PollResponse> getResult(UUID id) {
        Optional<Poll> poll = pollRepository.findById(id);

        return poll.map(value -> new PollResponse(pollRepository.getVoteCountByPoll(value.getId())));
    }
}
