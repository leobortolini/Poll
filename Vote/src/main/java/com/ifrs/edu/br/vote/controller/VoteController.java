package com.ifrs.edu.br.vote.controller;

import com.ifrs.edu.br.vote.service.VoteService;
import com.ifrs.edu.br.vote.util.response.PollResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("api/v1/vote")
@CrossOrigin
@Slf4j
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<PollResponse> getVotes(@PathVariable UUID identifier) {
        log.info("getVotes - start()");
        PollResponse pollResponse = voteService.getResult(identifier);

        return ResponseEntity.ok(pollResponse);
    }
}
