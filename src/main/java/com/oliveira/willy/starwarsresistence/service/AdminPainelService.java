package com.oliveira.willy.starwarsresistence.service;

import com.oliveira.willy.starwarsresistence.dto.AdminReport;
import com.oliveira.willy.starwarsresistence.model.Inventory;
import com.oliveira.willy.starwarsresistence.model.Item;
import com.oliveira.willy.starwarsresistence.model.Rebel;
import com.oliveira.willy.starwarsresistence.model.enums.ItemInventory;
import com.oliveira.willy.starwarsresistence.repository.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminPainelService {
    private final RebelRepository rebelRepository;

    public AdminReport issueReport() {
        List<Rebel> rebels = rebelRepository.findAll();
        Long quantityTotal = rebelRepository.count();
        int quantityOfTraitors = countTraitor(rebels);
        int quantidyOfRebels = (int) (quantityTotal - quantityOfTraitors);

        calculateAverageOfItemsPerRebel(rebels);

        return null;
    }

    private int countTraitor(List<Rebel> rebels) {
        return (int) rebels.stream().map(Rebel::getInventory)
                .filter((inventory -> inventory.isBlocked())).count();
    }

    private Map<ItemInventory, Double> calculateAverageOfItemsPerRebel(List<Rebel> rebels) {
        List<Item> items = rebels.stream().map(Rebel::getInventory)
                .filter((inventory -> !inventory.isBlocked()))
                .map(Inventory::getItems)
                .flatMap(List::stream).collect(Collectors.toList());

        Map<ItemInventory, Double> itemInventoryMap = items.stream()
                .collect(Collectors.groupingBy(Item::getName, Collectors.averagingInt(Item::getQuantity)));

        System.out.println(itemInventoryMap.toString());
        return itemInventoryMap;
    }

    private int countPointdLostBecauseOfTraitors() {
        return 1;
    }
}
