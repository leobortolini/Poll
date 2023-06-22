package com.ifrs.edu.br.poll.repository;

import com.ifrs.edu.br.poll.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Poll, Long> {
}
