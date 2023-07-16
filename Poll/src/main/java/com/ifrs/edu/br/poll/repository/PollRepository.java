package com.ifrs.edu.br.poll.repository;

import com.ifrs.edu.br.poll.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PollRepository extends JpaRepository<Poll, UUID> {
}
