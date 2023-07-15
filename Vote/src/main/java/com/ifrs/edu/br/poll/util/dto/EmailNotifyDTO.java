package com.ifrs.edu.br.poll.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EmailNotifyDTO implements Serializable {
    private UUID identifier;
    private String email;
}
