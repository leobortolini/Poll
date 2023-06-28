package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Option;
import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.repository.PollRepository;
import com.ifrs.edu.br.poll.util.VoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    @Override
    public Poll save(VoteDTO vote) {
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
    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    @Cacheable(value = "product", unless="#result == null")
    public Optional<Poll> findById(Integer id) {
        return pollRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "product", key = "#product.id")
    public Poll update(Poll product) {
        return pollRepository.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        pollRepository.deleteById(id);
    }
}
