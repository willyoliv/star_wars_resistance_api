package com.oliveira.willy.starwarsresistence.dto;

import com.oliveira.willy.starwarsresistence.model.enums.ItemInventory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Builder
@Getter
public class AdminReport {
    private double percentageOfTraitors;
    private double percentageOfRebels;
    private Map<ItemInventory, Double> averageOfItems;
    private int pointsLostBecauseTraitors;

}