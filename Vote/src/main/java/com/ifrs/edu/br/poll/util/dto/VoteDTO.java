package com.ifrs.edu.br.poll.util.dto;

import com.ifrs.edu.br.poll.util.request.VoteRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class VoteDTO implements Serializable {
    private UUID identifier;
    private VoteRequest voteRequest;
}
