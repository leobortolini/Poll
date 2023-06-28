package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.util.VoteDTO;

import java.util.List;
import java.util.Optional;

public interface PollService {
    Poll save(VoteDTO product);
    List<Poll> findAll();
    Poll update(Poll product);
    void deleteById(Integer id);
}
