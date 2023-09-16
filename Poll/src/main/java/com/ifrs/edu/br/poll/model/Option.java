package com.ifrs.edu.br.poll.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ifrs.edu.br.poll.util.encrypt.EncryptConverter;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Option implements Serializable {
    @Id
    @Generated
    private UUID id;
    @Convert(converter = EncryptConverter.class)
    private String title;
    @CreationTimestamp
    private LocalDateTime created_at;
    @ManyToOne
    @JoinColumn(name = "idpoll")
    @JsonBackReference
    private Poll poll;
}
