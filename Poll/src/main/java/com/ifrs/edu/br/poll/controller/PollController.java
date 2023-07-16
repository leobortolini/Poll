package com.ifrs.edu.br.poll.controller;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.service.PollService;
import com.ifrs.edu.br.poll.service.VoteService;
import com.ifrs.edu.br.poll.util.request.PollRequest;
import com.ifrs.edu.br.poll.util.request.VoteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/poll")
@Slf4j
public class PollController {
    private final PollService pollService;
    private final VoteService voteService;

    public PollController(PollService pollService, VoteService voteService) {
        this.pollService = pollService;
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<Poll> create(@RequestBody PollRequest vote) {
        log.info("create - start()");

        return ResponseEntity.status(HttpStatus.CREATED).body(pollService.save(vote));
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<Poll> findPollByIdentifier(@PathVariable UUID identifier) {
        log.info("findPollByIdentifier - start()");
        Optional<Poll> poll = pollService.findByIdentifier(identifier);

        return poll.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{identifier}")
    public ResponseEntity<Void> voteOnPoll(@PathVariable UUID identifier, @RequestBody VoteRequest vote) {
        log.info("voteOnPoll - start()");

        if (voteService.sendVote(identifier, vote)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.unprocessableEntity().build();
    }
}
