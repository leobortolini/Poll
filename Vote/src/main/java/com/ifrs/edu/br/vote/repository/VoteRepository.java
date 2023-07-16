package com.ifrs.edu.br.vote.repository;

import com.ifrs.edu.br.vote.model.Vote;
import com.ifrs.edu.br.vote.util.dto.VoteCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("SELECT new com.ifrs.edu.br.vote.util.dto.VoteCountDTO(vote.idoption, COUNT(*)) FROM Vote vote WHERE vote.idpoll = :pollId GROUP BY vote.idoption")
    List<VoteCountDTO> getVoteCountByPoll(UUID pollId);
}
