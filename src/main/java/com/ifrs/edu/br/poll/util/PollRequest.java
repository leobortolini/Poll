package com.ifrs.edu.br.poll.util;

import lombok.Data;

import java.util.List;

@Data
public class PollRequest {
    private String title;
    private String description;
    private List<String> options;
}
