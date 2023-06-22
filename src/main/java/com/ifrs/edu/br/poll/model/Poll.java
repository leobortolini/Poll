package com.ifrs.edu.br.poll.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Generated;


import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Poll implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    @Generated
    private UUID identifier;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "poll")
    @JsonManagedReference
    private List<Option> options;
}
