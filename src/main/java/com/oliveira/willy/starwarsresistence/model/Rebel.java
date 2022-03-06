package com.oliveira.willy.starwarsresistence.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.oliveira.willy.starwarsresistence.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rebel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @JsonManagedReference
    private Location location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    @JsonManagedReference
    private Inventory inventory;

    @OneToMany(mappedBy = "accused",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Report> report;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void save() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void update() {
        this.updatedAt = LocalDateTime.now();
    }
}
