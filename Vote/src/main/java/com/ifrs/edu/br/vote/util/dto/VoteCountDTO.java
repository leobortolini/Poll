package com.ifrs.edu.br.vote.util.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class VoteCountDTO implements Serializable {
    private UUID option;
    private Long count;

    public VoteCountDTO(UUID option, Long count) {
        this.option = option;
        this.count = count;
    }
}
