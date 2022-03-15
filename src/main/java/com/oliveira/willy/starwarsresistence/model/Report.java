package com.oliveira.willy.starwarsresistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor

@Builder
@Setter
@Getter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "accused_id")
    @JsonBackReference
    private Rebel accused;

    @ManyToOne()
    @JoinColumn(name = "accuser_id")
    @JsonBackReference
    private Rebel accuser;

    private String reason;
}
