package com.ifrs.edu.br.vote.util.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoteCountDTO implements Serializable {
    private Long option;
    private Long count;

    public VoteCountDTO(Long option, Long count) {
        this.option = option;
        this.count = count;
    }
}
