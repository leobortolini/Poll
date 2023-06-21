package com.ifrs.edu.br.poll.util;

import lombok.Data;

import java.util.List;

@Data
public class VoteDTO {
    private String title;
    private String description;
    private List<String> options;
}
