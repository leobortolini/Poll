package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class VoteServiceImpl implements VoteService {
    private final PollRepository pollRepository;

    @Autowired
    public VoteServiceImpl(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }
    @Override
    @Cacheable(value = "poll")
    public Optional<Poll> findByIdentifier(UUID identifier) {
        return pollRepository.findByUuid(identifier);
    }
}
