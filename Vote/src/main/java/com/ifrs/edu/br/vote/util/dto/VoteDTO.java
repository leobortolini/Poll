package com.ifrs.edu.br.vote.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

public record VoteDTO(@JsonProperty("identifier") UUID identifier, @JsonProperty("option") UUID option,
                      @JsonProperty("email") String email) implements Serializable { }
