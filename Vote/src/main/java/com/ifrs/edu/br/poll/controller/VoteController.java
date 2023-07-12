package com.ifrs.edu.br.poll.controller;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.service.VoteService;
import com.ifrs.edu.br.poll.util.request.VoteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("api/v1/vote")
@Slf4j
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/{identifier}")
    public ResponseEntity<Void> voteOnPoll(@PathVariable UUID identifier, @RequestBody VoteRequest vote) {
        log.info("voteOnPoll - start()");
        voteService.sendVote(identifier, vote);

        return ResponseEntity.ok().build();
    }
}
