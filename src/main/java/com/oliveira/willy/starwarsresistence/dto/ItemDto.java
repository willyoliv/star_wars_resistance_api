package com.oliveira.willy.starwarsresistence.dto;

import com.oliveira.willy.starwarsresistence.model.enums.ItemInventory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemDto {

    @NotNull(message = "The name field cannot be empty or null")
    private ItemInventory name;

    @NotNull(message = "The quantity field cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
}
