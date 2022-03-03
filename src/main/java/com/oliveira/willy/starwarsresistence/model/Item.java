package com.oliveira.willy.starwarsresistence.model;

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
    private int quantity;

    @ManyToOne
    @JoinColumn
    @Setter
    private Inventory inventory;

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
