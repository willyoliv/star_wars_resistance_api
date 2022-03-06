package com.oliveira.willy.starwarsresistence.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReportCreateDto {
    @NotNull(message = "The accusedId field cannot be null")
    private Long accusedId;

    @NotBlank(message = "The reason field cannot be empty or null")
    private String reason;
}
