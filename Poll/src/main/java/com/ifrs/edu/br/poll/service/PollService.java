package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.util.request.PollRequest;

import java.util.Optional;
import java.util.UUID;

public interface PollService {
    Poll save(PollRequest product);
    Optional<Poll> findByIdentifier(UUID identifier);
}
