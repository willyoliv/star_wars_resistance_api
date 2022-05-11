package com.oliveira.willy.starwarsresistance.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class InventoryDto {

    @NotNull(message = "The items field cannot be empty or null")
    @Size(min = 4, max = 4, message = "The items field must contain four items: { WEAPON, MUNITION, WATER and FOOD}")
    @Valid
    private List<ItemDto> items;
}
