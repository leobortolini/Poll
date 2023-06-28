package com.ifrs.edu.br.poll.repository;

import com.ifrs.edu.br.poll.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PollRepository extends JpaRepository<Poll, Integer> {
    @Query("SELECT poll FROM Poll poll WHERE poll.identifier = :identifier")
    Optional<Poll> findByUuid(@Param("identifier") UUID identifier);
}
