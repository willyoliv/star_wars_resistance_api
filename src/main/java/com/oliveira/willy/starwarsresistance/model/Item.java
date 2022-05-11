package com.oliveira.willy.starwarsresistance.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.oliveira.willy.starwarsresistance.model.enums.ItemInventory;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
