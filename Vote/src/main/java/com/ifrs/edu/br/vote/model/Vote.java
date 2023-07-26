package com.ifrs.edu.br.vote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID idpoll;
    private Long idoption;
    @CreationTimestamp
    private LocalDateTime created_at;
    @Transient
    @JsonIgnore
    private String email;
}
