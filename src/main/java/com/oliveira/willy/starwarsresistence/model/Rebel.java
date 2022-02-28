package com.oliveira.willy.starwarsresistence.model;

import com.oliveira.willy.starwarsresistence.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private Genre genre;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "localization_id", referencedColumnName = "id")
    private Localization localization;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

//    @OneToMany(mappedBy = "rebel", cascade = CascadeType.ALL)
//    private List<Item> inventory;
//
//    public void setRebelToItem() {
//        if (!inventory.isEmpty()) {
//            for (Item item : inventory) {
//                item.setRebel(this);
//            }
//        }
//    }
}
