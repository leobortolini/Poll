package com.ifrs.edu.br.poll.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idpoll")
    private Poll poll;
    @ManyToOne
    @JoinColumn(name = "idoption")
    private Option option;
}
