package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Option;
import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.queue.QueueSender;
import com.ifrs.edu.br.poll.repository.PollRepository;
import com.ifrs.edu.br.poll.util.dto.VoteDTO;
import com.ifrs.edu.br.poll.util.request.PollRequest;
import com.ifrs.edu.br.poll.util.request.VoteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;
    private final QueueSender queueSender;

    public PollServiceImpl(PollRepository pollRepository, QueueSender queueSender) {
        this.pollRepository = pollRepository;
        this.queueSender = queueSender;
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
    public void sendVote(UUID identifier, VoteRequest request) {
        queueSender.sendVote(new VoteDTO(identifier, request.getOptionId(), request.getEmail()));
    }
}
