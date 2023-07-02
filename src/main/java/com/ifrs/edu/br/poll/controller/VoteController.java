package com.ifrs.edu.br.poll.controller;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.queue.QueueSender;
import com.ifrs.edu.br.poll.service.VoteService;
import com.ifrs.edu.br.poll.util.dto.VoteDTO;
import com.ifrs.edu.br.poll.util.request.VoteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
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
    private final QueueSender queueSender;

    public VoteController(VoteService voteService, QueueSender queueSender) {
        this.voteService = voteService;
        this.queueSender = queueSender;
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<Poll> findPollByIdentifier(@PathVariable UUID identifier) {
        LOGGER.info("findPollByIdentifier - start()");
        Optional<Poll> poll = voteService.findPollByIdentifier(identifier);

        return poll.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{identifier}")
    public ResponseEntity<Void> voteOnPoll(@PathVariable UUID identifier, @RequestBody VoteRequest vote) {
        LOGGER.info("voteOnPoll - start()");
        VoteDTO newVote = new VoteDTO(identifier, vote);
        try {
            queueSender.send("test-exchange", "routing-key-teste", newVote);
        } catch (AmqpException ex) {
            LOGGER.error("voteOnPoll - error sending vote to queue", ex);

            return ResponseEntity.internalServerError().build();
        }
        LOGGER.debug("voteOnPoll - end()");

        return ResponseEntity.ok().build();
    }
}
