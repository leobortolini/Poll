package com.ifrs.edu.br.poll.controller;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.model.Vote;
import com.ifrs.edu.br.poll.queue.QueueSender;
import com.ifrs.edu.br.poll.service.PollService;
import com.ifrs.edu.br.poll.util.dto.VoteDTO;
import com.ifrs.edu.br.poll.util.request.VoteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("api/v1/vote")
public class VoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteController.class);
    private final PollService pollService;
    private final QueueSender queueSender;

    public VoteController(PollService pollService, QueueSender queueSender) {
        this.pollService = pollService;
        this.queueSender = queueSender;
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<Optional<Poll>> findByIdentifier(@PathVariable UUID identifier) {
        LOGGER.info("start() - findByUuid");
        Optional<Poll> poll = pollService.findByIdentifier(identifier);

        if (poll.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(poll);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{identifier}")
    public ResponseEntity<Optional<Vote>> voteOnPoll(@PathVariable UUID identifier, @RequestBody VoteRequest vote) {
        LOGGER.info("start() - voteOnPoll");
        VoteDTO newVote = new VoteDTO(identifier, vote);

        queueSender.send("test-exchange", "routing-key-teste", newVote);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
