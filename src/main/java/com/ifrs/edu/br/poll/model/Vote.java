package com.ifrs.edu.br.poll.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Vote implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "vote_option",
            joinColumns = @JoinColumn(name = "idvote"),
            inverseJoinColumns = @JoinColumn(name = "idoption"))
    @JsonManagedReference
    private List<Option> options;
}
