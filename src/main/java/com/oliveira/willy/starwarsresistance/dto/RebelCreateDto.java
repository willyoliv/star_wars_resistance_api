package com.oliveira.willy.starwarsresistance.dto;

import com.oliveira.willy.starwarsresistance.model.enums.Genre;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RebelCreateDto {
    @NotEmpty(message = "The name field cannot be empty or null")
    private String name;

    @NotNull(message = "The age field cannot be null")
    private int age;

    @NotNull(message = "The genre field cannot be null")
    private Genre genre;

    @NotNull(message = "The localization field cannot be null")
    @Valid
    private LocationDto location;

    @NotNull(message = "The inventory field cannot be null")
    @Valid
    private InventoryDto inventory;
}
