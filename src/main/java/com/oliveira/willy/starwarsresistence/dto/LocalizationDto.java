package com.oliveira.willy.starwarsresistence.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LocalizationDto {
    @NotEmpty(message = "The galaxyName field cannot be empty or null")
    private String galaxyName;

    @NotNull(message = "The latitude field cannot be null")
    private float latitude;

    @NotNull(message = "The longitude field cannot be null")
    private float longitude;
}
