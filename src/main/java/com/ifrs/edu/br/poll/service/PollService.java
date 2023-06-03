package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.util.PollRequest;

public interface PollService {
    Poll save(PollRequest product);
}
