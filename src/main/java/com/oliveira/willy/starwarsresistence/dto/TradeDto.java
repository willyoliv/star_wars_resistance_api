package com.oliveira.willy.starwarsresistence.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TradeDto {
    @NotNull(message = "The fromRebel field cannot be empty or null")
    private RebelTradeDto fromRebel;

    @NotNull(message = "The toRebelId field cannot be empty or null")
    private RebelTradeDto toRebel;
}