package com.ifrs.edu.br.poll.util.request;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class VoteRequest implements Serializable {
    private UUID optionId;
    private String email;
}
