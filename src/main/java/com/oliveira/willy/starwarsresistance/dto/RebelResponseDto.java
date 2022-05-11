package com.oliveira.willy.starwarsresistance.dto;

import com.oliveira.willy.starwarsresistance.model.enums.Genre;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RebelResponseDto {

    private Long id;

    private String name;

    private int age;

    private Genre genre;

    private LocationResponseDto location;

    private InventoryResponseDto inventory;

    private List<ReportResponseDto> report;

    private boolean traitor;
}
