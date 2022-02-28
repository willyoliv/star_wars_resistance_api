package com.oliveira.willy.starwarsresistence.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Localization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String galaxyName;

    private float latitude;
    private float longitude;

    @OneToOne(mappedBy = "localization")
    private Rebel rebel;
}
