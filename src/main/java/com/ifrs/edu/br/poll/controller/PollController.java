package com.ifrs.edu.br.poll.controller;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.service.PollService;
import com.ifrs.edu.br.poll.util.request.PollRequest;
import com.ifrs.edu.br.poll.util.response.PollResponse;
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

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping
    public ResponseEntity<Poll> create(@RequestBody PollRequest vote) {
        log.info("create - start()");

        return ResponseEntity.status(HttpStatus.CREATED).body(pollService.save(vote));
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<PollResponse> getVotes(@PathVariable UUID identifier) {
        log.info("getVotes - start()");
        Optional<PollResponse> pollResponse = pollService.getResult(identifier);

        return pollResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
