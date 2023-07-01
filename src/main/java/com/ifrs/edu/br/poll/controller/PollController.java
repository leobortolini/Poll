package com.ifrs.edu.br.poll.controller;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.service.PollService;
import com.ifrs.edu.br.poll.util.request.PollRequest;
import com.ifrs.edu.br.poll.util.response.PollResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/poll")
public class PollController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollController.class);
    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping
    public ResponseEntity<Poll> create(@RequestBody PollRequest vote) {
        LOGGER.info("start() - create");

        return ResponseEntity.status(HttpStatus.CREATED).body(pollService.save(vote));
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<PollResponse> getVotes(@PathVariable UUID identifier) {
        LOGGER.info("start() - getVotes");
        Optional<PollResponse> pollResponse = pollService.getResult(identifier);

        return pollResponse.map(response -> ResponseEntity.status(HttpStatus.OK).body(response)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
