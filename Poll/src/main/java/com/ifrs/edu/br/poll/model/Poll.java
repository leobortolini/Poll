package com.ifrs.edu.br.poll.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ifrs.edu.br.poll.util.encrypt.EncryptConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Poll implements Serializable {
    @Id
    @Generated
    private UUID id;
    @Convert(converter = EncryptConverter.class)
    private String title;
    @Convert(converter = EncryptConverter.class)
    private String description;
    @CreationTimestamp
    private LocalDateTime created_at;
    private LocalDateTime expire_date;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "poll")
    @JsonManagedReference
    private List<Option> options;
}
