package com.ifrs.edu.br.poll.repository;

import com.ifrs.edu.br.poll.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
