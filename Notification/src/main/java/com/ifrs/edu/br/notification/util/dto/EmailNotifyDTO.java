package com.ifrs.edu.br.notification.util.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

public record EmailNotifyDTO (@JsonProperty("identifier") UUID identifier,
                              @JsonProperty("email") String email,
                              @JsonProperty("vote") UUID vote) implements Serializable { }
