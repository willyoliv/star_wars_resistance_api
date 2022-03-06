package com.oliveira.willy.starwarsresistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.oliveira.willy.starwarsresistence.model.enums.ItemInventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemInventory name;

    private int quantity;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Inventory inventory;
}
