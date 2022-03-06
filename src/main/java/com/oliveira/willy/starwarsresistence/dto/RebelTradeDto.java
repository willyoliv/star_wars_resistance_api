package com.oliveira.willy.starwarsresistence.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class RebelTradeDto {
    @NotNull(message = "The rebelId field cannot be empty or null")
    private Long rebelId;

    @NotNull(message = "The items field cannot be empty or null")
    @Size(min = 1, max = 4, message = "The items field must contain between 1 to 4 items")
    @Valid
    private List<ItemDto> items;
}
