package com.ifrs.edu.br.poll.service;


import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.util.request.VoteRequest;

import java.util.Optional;
import java.util.UUID;

public interface VoteService {
    Optional<Poll> findPollByIdentifier(UUID identifier);
    void sendVote(UUID identifier, VoteRequest vote);
}
