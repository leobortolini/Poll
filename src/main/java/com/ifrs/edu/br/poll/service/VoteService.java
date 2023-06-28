package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Poll;

import java.util.Optional;
import java.util.UUID;

public interface VoteService {
    Optional<Poll> findByIdentifier(UUID identifier);
}
