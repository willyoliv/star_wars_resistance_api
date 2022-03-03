package com.oliveira.willy.starwarsresistence.dto;

import com.oliveira.willy.starwarsresistence.model.enums.ItemInventory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemDto {

    @NotEmpty(message = "The name field cannot be empty or null")
    private ItemInventory name;

    @NotNull(message = "The value field cannot be null")
    private int quantity;
}
