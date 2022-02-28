package com.oliveira.willy.starwarsresistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;


    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    @Setter
    @Getter
    private List<Item> items;

    @OneToOne(mappedBy = "inventory")
    @Setter
    private Rebel rebel;

    public void setInventoryToItem() {
        if (!items.isEmpty()) {
            for (Item item : items) {
                item.setInventory(this);
            }
        }
    }
}
