package com.ifrs.edu.br.poll.util.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PollRequest {
    private String title;
    private String description;
    private LocalDateTime expireDateTime;
    private List<String> options;
}
