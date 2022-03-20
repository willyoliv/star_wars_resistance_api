package com.oliveira.willy.starwarsresistence.service;

import com.oliveira.willy.starwarsresistence.dto.AdminReport;
import com.oliveira.willy.starwarsresistence.model.Inventory;
import com.oliveira.willy.starwarsresistence.model.Item;
import com.oliveira.willy.starwarsresistence.model.Rebel;
import com.oliveira.willy.starwarsresistence.model.enums.ItemInventory;
import com.oliveira.willy.starwarsresistence.repository.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final RebelRepository rebelRepository;

    public AdminReport report() {
        List<Rebel> rebels = rebelRepository.findAll();
        int quantityTotal = rebels.size();
        int quantityOfTraitors = countTraitor(rebels);
        int quantityOfRebels = quantityTotal - quantityOfTraitors;

        Map<ItemInventory, Double> mapAverage = calculateAverageOfItemsPerRebel(rebels);

        int sumLostPoints = countLostPointsBecauseOfTraitors(rebels);
        Double percentageOfRebels = (double) quantityOfRebels / quantityTotal;
        Double percentageOfTraitors = (double) quantityOfTraitors / quantityTotal;

        return AdminReport.builder()
                .averageOfItems(mapAverage)
                .percentageOfRebels(Double.isNaN(percentageOfRebels) ? 0.0 : percentageOfRebels)
                .percentageOfTraitors(Double.isNaN(percentageOfTraitors) ? 0.0 : percentageOfTraitors)
                .lostPoints(sumLostPoints)
                .build();
    }

    private int countTraitor(List<Rebel> rebels) {
        return rebels.stream().filter(Rebel::isTraitor).collect(Collectors.toList()).size();
    }

    private Map<ItemInventory, Double> calculateAverageOfItemsPerRebel(List<Rebel> rebels) {

        List<Item> items = rebels.stream().filter((rebel -> !rebel.isTraitor()))
                .map(Rebel::getInventory)
                .map(Inventory::getItems)
                .flatMap(List::stream).collect(Collectors.toList());

        Map<ItemInventory, Double> itemInventoryMap = items.stream()
                .collect(Collectors.groupingBy(Item::getName, Collectors.averagingInt(Item::getQuantity)));

        return itemInventoryMap;
    }

    private int countLostPointsBecauseOfTraitors(List<Rebel> rebels) {
        int sum = rebels.stream().filter(Rebel::isTraitor)
                .map(Rebel::getInventory)
                .map(Inventory::getItems)
                .flatMap(List::stream).collect(Collectors.toList())
                .stream().mapToInt(Item::getQuantity).sum();
        return sum;
    }
}
