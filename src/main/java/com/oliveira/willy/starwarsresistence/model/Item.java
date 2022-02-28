package com.oliveira.willy.starwarsresistence.model;

import com.oliveira.willy.starwarsresistence.model.enums.ItemInventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private ItemInventory name;

    @Getter
    @Setter
    private int value;

    @ManyToOne
    @JoinColumn
    @Setter
    private Inventory inventory;

}
