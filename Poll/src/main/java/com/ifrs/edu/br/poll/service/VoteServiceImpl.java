package com.ifrs.edu.br.poll.service;

import com.ifrs.edu.br.poll.model.Poll;
import com.ifrs.edu.br.poll.queue.QueueSender;
import com.ifrs.edu.br.poll.util.dto.VoteDTO;
import com.ifrs.edu.br.poll.util.request.VoteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class VoteServiceImpl implements VoteService {

    private final QueueSender queueSender;
    private final PollService pollService;

    public VoteServiceImpl(QueueSender queueSender, PollService pollService) {
        this.queueSender = queueSender;
        this.pollService = pollService;
    }

    @Override
    public boolean sendVote(UUID identifier, VoteRequest request) {
        log.info("sendVote - start()");
        Optional<Poll> poll = pollService.findByIdentifier(identifier);

        if (poll.isPresent() && poll.get().getExpire_date().isAfter(LocalDateTime.now()) && poll.get().getOptions().stream().anyMatch(option -> Objects.equals(option.getId(), request.getOptionId()))) {
            queueSender.sendVote(new VoteDTO(identifier, request.getOptionId(), request.getEmail()));

            return true;
        }

        return false;
    }
}
