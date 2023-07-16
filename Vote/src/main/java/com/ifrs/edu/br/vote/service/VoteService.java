package com.ifrs.edu.br.vote.service;

import com.ifrs.edu.br.vote.util.response.PollResponse;

import java.util.Optional;
import java.util.UUID;

public interface VoteService {
    Optional<PollResponse> getResult(UUID id);
}
