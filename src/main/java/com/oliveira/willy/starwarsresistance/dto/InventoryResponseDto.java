package com.oliveira.willy.starwarsresistance.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InventoryResponseDto {

    private Long id;

    private List<ItemDto> items;
}
