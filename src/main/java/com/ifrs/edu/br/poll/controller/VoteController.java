package com.ifrs.edu.br.poll.controller;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.queue.QueueSender;
import com.ifrs.edu.br.poll.service.VoteService;
import com.ifrs.edu.br.poll.util.VoteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/vote")
public class VoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteController.class);
    private final VoteService voteService;
    private final QueueSender queueSender;


    @Autowired
    public VoteController(VoteService voteService, QueueSender queueSender) {
        this.voteService = voteService;
        this.queueSender = queueSender;
    }

    @GetMapping
    public ResponseEntity<List<Poll>> findAll() {
        LOGGER.info("start() - findAll");
        return ResponseEntity.status(HttpStatus.OK).body(voteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Poll>> findById(@PathVariable Long id) {
        LOGGER.info("start() - findById");
        return ResponseEntity.status(HttpStatus.OK).body(voteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Poll> create(@RequestBody VoteDTO vote) {
        LOGGER.info("start() - create");
        String text = "test message";

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("ultima", "sim");
        Message message = new Message(text.getBytes(), messageProperties);

        queueSender.send("test-exchange", "routing-key-teste", message);

        return ResponseEntity.status(HttpStatus.CREATED).body(voteService.save(vote));
    }

    @PutMapping
    public ResponseEntity<Poll> update(@RequestBody Poll product) {
        LOGGER.info("start() - update");
        return ResponseEntity.status(HttpStatus.OK).body(voteService.update(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        LOGGER.info("start() - delete");
        voteService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
