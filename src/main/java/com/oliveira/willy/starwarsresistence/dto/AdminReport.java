package com.oliveira.willy.starwarsresistence.dto;

import com.oliveira.willy.starwarsresistence.model.enums.ItemInventory;
import lombok.Builder;
import lombok.Setter;

import java.util.LinkedHashMap;

@Setter
@Builder
public class AdminReport {
    private double percentageOfTraitors;
    private double percentageOfRebels;
    private LinkedHashMap<ItemInventory, Double> averageOfItems;
    private int pointsLostBecauseTraitors;

}
