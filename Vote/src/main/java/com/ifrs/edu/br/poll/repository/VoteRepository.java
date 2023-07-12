package com.ifrs.edu.br.poll.repository;

import com.ifrs.edu.br.poll.model.Vote;
import com.ifrs.edu.br.poll.util.dto.VoteCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("SELECT new com.ifrs.edu.br.poll.util.dto.VoteCount(vote.option, COUNT(*)) FROM Vote vote WHERE vote.poll.id = :pollId GROUP BY vote.option")
    List<VoteCount> getVoteCountByPoll(UUID pollId);
}
