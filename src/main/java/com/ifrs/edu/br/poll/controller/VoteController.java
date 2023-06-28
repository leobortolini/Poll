package com.ifrs.edu.br.poll.controller;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
