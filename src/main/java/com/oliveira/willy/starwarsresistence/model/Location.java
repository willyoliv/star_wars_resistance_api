package com.oliveira.willy.starwarsresistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String galaxyName;

    private Long latitude;

    private Long longitude;

    @OneToOne(mappedBy = "location")
    @JsonBackReference
    private Rebel rebel;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
