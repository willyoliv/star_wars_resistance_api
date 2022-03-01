package com.oliveira.willy.starwarsresistence.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class InventoryDto {

    @NotEmpty(message = "The items field cannot be empty or null")
    private List<ItemDto> items;
}
