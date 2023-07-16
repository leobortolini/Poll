package com.ifrs.edu.br.vote.service;

import com.ifrs.edu.br.vote.util.response.PollResponse;

import java.util.UUID;

public interface VoteService {
    PollResponse getResult(UUID id);
}
