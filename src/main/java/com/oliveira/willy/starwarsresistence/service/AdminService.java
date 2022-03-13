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

    Logger logger = LoggerFactory.getLogger(AdminService.class);

    public AdminReport issueReport() {
        List<Rebel> rebels = rebelRepository.findAll();
        int quantityTotal = rebels.size();
        int quantityOfTraitors = countTraitor(rebels);
        int quantityOfRebels = quantityTotal - quantityOfTraitors;

        Map<ItemInventory, Double> mapAverage = calculateAverageOfItemsPerRebel(rebels);

        int sumLostPoints = countPointdLostBecauseOfTraitors(rebels);

        return AdminReport.builder()
                .averageOfItems(mapAverage)
                .percentageOfRebels((double) quantityOfRebels / quantityTotal)
                .percentageOfTraitors((double) quantityOfTraitors / quantityTotal)
                .pointsLostBecauseTraitors(sumLostPoints)
                .build();
    }

    private int countTraitor(List<Rebel> rebels) {
        return rebels.stream().map(Rebel::getInventory)
                .filter((inventory -> inventory.isBlocked())).collect(Collectors.toList()).size();
    }

    private Map<ItemInventory, Double> calculateAverageOfItemsPerRebel(List<Rebel> rebels) {
        List<Item> items = rebels.stream().map(Rebel::getInventory)
                .filter((inventory -> !inventory.isBlocked()))
                .map(Inventory::getItems)
                .flatMap(List::stream).collect(Collectors.toList());

        Map<ItemInventory, Double> itemInventoryMap = items.stream()
                .collect(Collectors.groupingBy(Item::getName, Collectors.averagingInt(Item::getQuantity)));

        return itemInventoryMap;
    }

    private int countPointdLostBecauseOfTraitors(List<Rebel> rebels) {
        int sum = rebels.stream().map(Rebel::getInventory)
                .filter((inventory -> inventory.isBlocked()))
                .map(Inventory::getItems)
                .flatMap(List::stream).collect(Collectors.toList())
                .stream().mapToInt(Item::getQuantity).sum();
        return sum;
    }
}
