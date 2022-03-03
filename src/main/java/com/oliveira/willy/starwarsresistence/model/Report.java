package com.oliveira.willy.starwarsresistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "accused_id")
    private Rebel accused;

    @ManyToOne()
    @JoinColumn(name = "accuser_id")
    private Rebel accuser;

    @Getter
    private String reason;
}
