package com.ifrs.edu.br.poll.util.dto;

import com.ifrs.edu.br.poll.model.Option;
import lombok.Data;

import java.io.Serializable;

@Data
public class VoteCount implements Serializable {
    private Option option;
    private Long count;

    public VoteCount(Option option, Long count) {
        this.option = option;
        this.count = count;
    }
}
