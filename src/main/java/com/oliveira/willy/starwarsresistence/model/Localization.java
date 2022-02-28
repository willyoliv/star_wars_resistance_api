package com.oliveira.willy.starwarsresistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Localization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String galaxyName;

    @Getter
    @Setter
    private float latitude;

    @Getter
    @Setter
    private float longitude;

    @OneToOne(mappedBy = "localization")
    @Setter
    private Rebel rebel;
}
