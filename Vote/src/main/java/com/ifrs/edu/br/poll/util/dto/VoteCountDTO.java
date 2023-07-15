package com.ifrs.edu.br.poll.util.dto;

import com.ifrs.edu.br.poll.model.Option;
import lombok.Data;

import java.io.Serializable;

@Data
public class VoteCountDTO implements Serializable {
    private Option option;
    private Long count;

    public VoteCountDTO(Option option, Long count) {
        this.option = option;
        this.count = count;
    }
}
