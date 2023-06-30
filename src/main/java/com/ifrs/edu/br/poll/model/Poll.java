package com.ifrs.edu.br.poll.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ifrs.edu.br.poll.util.encrypt.Encrypt;
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
    private Long id;
    @Convert(converter = Encrypt.class)
    private String title;
    @Convert(converter = Encrypt.class)
    private String description;
    @Generated
    private UUID identifier;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "poll")
    @JsonManagedReference
    private List<Option> options;
}
