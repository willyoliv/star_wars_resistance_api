package com.oliveira.willy.starwarsresistence.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponseDto {

    private Long id;

    private String galaxyName;

    private Long latitude;

    private Long longitude;
}
