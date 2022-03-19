package com.oliveira.willy.starwarsresistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Item> items;

    @OneToOne(mappedBy = "inventory")
    @JsonBackReference
    private Rebel rebel;

    public void setInventoryToItem() {
        if (!items.isEmpty()) {
            for (Item item : items) {
                item.setInventory(this);
            }
        }
    }

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
