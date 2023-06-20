package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Vote;
import com.ifrs.edu.br.poll.util.VoteDTO;

import java.util.List;
import java.util.Optional;

public interface VoteService {
    Vote save(VoteDTO product);
    List<Vote> findAll();
    Optional<Vote> findById(Long id);
    Vote update(Vote product);
    void deleteById(Long id);
}
