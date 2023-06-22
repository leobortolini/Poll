package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Option;
import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.repository.VoteRepository;
import com.ifrs.edu.br.poll.util.VoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
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

        return voteRepository.save(newPoll);
    }

    @Override
    public List<Poll> findAll() {
        return voteRepository.findAll();
    }

    @Override
    @Cacheable(value = "product", unless="#result == null")
    public Optional<Poll> findById(Integer id) {
        return voteRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "product", key = "#product.id")
    public Poll update(Poll product) {
        return voteRepository.save(product);
    }

    @Override
    public void deleteById(Integer id) {
        voteRepository.deleteById(id);
    }
}
