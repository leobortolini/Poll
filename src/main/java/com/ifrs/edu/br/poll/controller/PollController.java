package com.ifrs.edu.br.poll.controller;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.queue.QueueSender;
import com.ifrs.edu.br.poll.service.PollService;
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
@RequestMapping("api/v1/poll")
public class PollController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollController.class);
    private final PollService pollService;
    private final QueueSender queueSender;

    @Autowired
    public PollController(PollService pollService, QueueSender queueSender) {
        this.pollService = pollService;
        this.queueSender = queueSender;
    }

    @PostMapping
    public ResponseEntity<Poll> create(@RequestBody VoteDTO vote) {
        LOGGER.info("start() - create");
        String text = "test message";

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("ultima", "sim");
        Message message = new Message(text.getBytes(), messageProperties);

        queueSender.send("test-exchange", "routing-key-teste", message);

        return ResponseEntity.status(HttpStatus.CREATED).body(pollService.save(vote));
    }

    @PutMapping
    public ResponseEntity<Poll> update(@RequestBody Poll product) {
        LOGGER.info("start() - update");
        return ResponseEntity.status(HttpStatus.OK).body(pollService.update(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        LOGGER.info("start() - delete");
        pollService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
