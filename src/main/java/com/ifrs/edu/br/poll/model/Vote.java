package com.ifrs.edu.br.poll.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idpoll")
    private Poll poll;
    @ManyToOne
    @JoinColumn(name = "idoption")
    private Option option;
}
