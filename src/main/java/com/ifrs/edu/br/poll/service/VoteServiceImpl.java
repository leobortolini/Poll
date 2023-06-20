package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Option;
import com.ifrs.edu.br.poll.model.Vote;
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
    public Vote save(VoteDTO vote) {
        Vote newVote = new Vote();

        newVote.setTitle(vote.getTitle());
        newVote.setDescription(vote.getDescription());

        List<Option> options = vote.getOptions().stream().map(option -> {
            Option newOption = new Option();

            newOption.setTitle(option);
            newOption.setVote(newVote);

            return newOption;
        }).toList();

        newVote.setOptions(options);

        return voteRepository.save(newVote);
    }

    @Override
    public List<Vote> findAll() {
        return voteRepository.findAll();
    }

    @Override
    @Cacheable(value = "product", unless="#result == null")
    public Optional<Vote> findById(Long id) {
        return voteRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "product", key = "#product.id")
    public Vote update(Vote product) {
        return voteRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        voteRepository.deleteById(id);
    }
}
