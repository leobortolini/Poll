package com.ifrs.edu.br.vote.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

public record EmailNotifyDTO (@JsonProperty("identifier") UUID identifier, @JsonProperty("email") String email) implements Serializable { }
