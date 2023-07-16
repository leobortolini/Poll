package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.util.request.VoteRequest;

import java.util.UUID;

public interface VoteService {
    boolean sendVote(UUID identifier, VoteRequest request);
}
