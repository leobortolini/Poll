package com.ifrs.edu.br.poll.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ifrs.edu.br.poll.util.encrypt.Encrypt;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Option implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = Encrypt.class)
    private String title;
    @ManyToOne
    @JoinColumn(name = "idvote")
    @JsonBackReference
    private Poll poll;
}
