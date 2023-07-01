package com.ifrs.edu.br.poll.util.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoteRequest implements Serializable {
    private Long optionId;
}
