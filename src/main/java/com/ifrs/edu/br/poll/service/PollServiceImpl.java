package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Option;
import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.repository.PollRepository;
import com.ifrs.edu.br.poll.util.PollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;

    @Autowired
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
}
