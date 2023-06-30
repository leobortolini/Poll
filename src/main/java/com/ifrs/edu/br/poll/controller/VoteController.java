package com.ifrs.edu.br.poll.controller;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.model.Vote;
import com.ifrs.edu.br.poll.service.VoteService;
import com.ifrs.edu.br.poll.util.request.VoteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<Optional<Poll>> findByIdentifier(@PathVariable UUID identifier) {
        LOGGER.info("start() - findByUuid");
        Optional<Poll> poll = voteService.findByIdentifier(identifier);

        if (poll.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(poll);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{identifier}")
    public ResponseEntity<Optional<Vote>> voteOnPoll(@PathVariable UUID identifier, @RequestBody VoteRequest vote) {
        LOGGER.info("start() - voteOnPoll");

        Optional<Vote> option = voteService.voteOnPoll(identifier, vote.getOptionId());

        if (option.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(option);
        }

        return ResponseEntity.notFound().build();
    }
}
