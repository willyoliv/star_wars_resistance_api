package com.oliveira.willy.starwarsresistance.dto;

import com.oliveira.willy.starwarsresistance.model.enums.ItemInventory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Builder
@Getter
public class AdminReport {
    private Double percentageOfTraitors;
    private Double percentageOfRebels;
    private Map<ItemInventory, Double> averageOfItems;
    private int lostPoints;


}
