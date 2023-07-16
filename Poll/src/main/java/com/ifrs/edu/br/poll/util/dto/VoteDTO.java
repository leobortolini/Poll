package com.ifrs.edu.br.poll.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

public record VoteDTO(@JsonProperty("identifier") UUID identifier, @JsonProperty("option") Long option,
                      @JsonProperty("email") String email) implements Serializable { }

