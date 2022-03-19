package com.oliveira.willy.starwarsresistence.service;

import com.oliveira.willy.starwarsresistence.exception.DuplicateItemsInventoryException;
import com.oliveira.willy.starwarsresistence.exception.InvalidReportException;
import com.oliveira.willy.starwarsresistence.exception.InvalidTradeException;
import com.oliveira.willy.starwarsresistence.exception.RebelNotFoundException;
import com.oliveira.willy.starwarsresistence.model.*;
import com.oliveira.willy.starwarsresistence.model.enums.ItemInventory;
import com.oliveira.willy.starwarsresistence.repository.LocationRepository;
import com.oliveira.willy.starwarsresistence.repository.RebelRepository;
import com.oliveira.willy.starwarsresistence.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RebelService {
    private final RebelRepository rebelRepository;

    private final LocationRepository locationRepository;

    private final ReportRepository reportRepository;

    private final int inventorySize = ItemInventory.values().length;

    Logger logger = LoggerFactory.getLogger(RebelService.class);

    @Value("${maximumNumberOfReport}")
    private int maximumNumberOfReport;

    public List<Rebel> findAllRebels() {
        return rebelRepository.findAll();
    }

    @Transactional
    public Rebel saveRebel(Rebel rebel) {
        int itemsInRebelInventorySize = countDistinctItemsInInventory(rebel.getInventory().getItems());

        if (itemsInRebelInventorySize != inventorySize) {
            logger.error("There are duplicate items in the inventory");
            throw new DuplicateItemsInventoryException("There are duplicate items in the inventory");
        }
        rebel.getInventory().setInventoryToItem();
        return rebelRepository.save(rebel);
    }

    public Rebel findRebelById(Long id) {
        return rebelRepository.findById(id).orElseThrow(() -> new RebelNotFoundException("Rebel ID " + id + " not found!"));
    }

    public Location updateRebelLocation(Long rebelId, Location location) {
        Rebel rebel = findRebelById(rebelId);
        location.setId(rebel.getLocation().getId());
        location.setCreatedAt(rebel.getLocation().getCreatedAt());

        return locationRepository.save(location);
    }

    public void reportRebelTraitor(Rebel accuser, Rebel accused, String reason) {

        if (accused.getId().equals(accuser.getId())) {
            throw new InvalidReportException("The rebel cannot self-report.");
        }

        Optional<Report> accuserFound = reportRepository.findByAccusedAndAccuser(accused, accuser);
        if (!accuserFound.isEmpty()) {
            throw new InvalidReportException("Report already registered. It is not possible to report this rebel again.");
        }

        Report report = Report.builder()
                .accuser(accuser)
                .accused(accused)
                .reason(reason)
                .build();
        accused.getReport().add(report);

        if (accused.getReport().size() == maximumNumberOfReport) {
            accused.setTraitor(true);
        }
        rebelRepository.save(accused);
    }

    public void trade(Rebel fromRebel, Rebel toRebel, List<Item> fromRebelItems, List<Item> toRebelItems) {
        checkIfRebelIsATraitor(fromRebel);
        checkIfRebelIsATraitor(toRebel);

        if (fromRebel.getId().equals(toRebel.getId())) {
            throw new InvalidTradeException("The rebel cannot trade with himself.");
        }

        validateDuplicateItemsInRequestInventory(fromRebelItems);
        validateDuplicateItemsInRequestInventory(toRebelItems);

        if (!validateQuantityOfItemsInInventory(fromRebel, fromRebelItems)) {
            throw new InvalidTradeException("The rebel ID " + fromRebel.getId() + " doesn't have enough for this trade. " +
                    "The quantity of one of the items exceeds the saved quantity.");
        }

        if (!validateQuantityOfItemsInInventory(toRebel, toRebelItems)) {
            throw new InvalidTradeException("The rebel ID " + toRebel.getId() + " doesn't have enough for this trade. " +
                    "The quantity of one of the items exceeds the saved quantity.");
        }

        int fromRebelItemsPoints = getSumOfItemPoints(fromRebelItems);
        int toRebelItemsPoints = getSumOfItemPoints(toRebelItems);

        if (toRebelItemsPoints != fromRebelItemsPoints) {
            throw new InvalidTradeException("The sum of rebel item points are not equivalent.");
        }

       addTradeItems(fromRebel.getInventory().getItems(), toRebelItems);
       removeTradeItems(fromRebel.getInventory().getItems(), fromRebelItems);
       addTradeItems(toRebel.getInventory().getItems(), fromRebelItems);
       removeTradeItems(toRebel.getInventory().getItems(), toRebelItems);

       rebelRepository.saveAll(List.of(fromRebel, toRebel));
    }



    private void checkIfRebelIsATraitor(Rebel rebel) {
        if (rebel.isTraitor()) {
            throw new InvalidTradeException("Trade invalid. The rebel ID " + rebel.getId() + " is a traitor! Be careful!");
        }
    }

    private void validateDuplicateItemsInRequestInventory(List<Item> rebelItemsFromRequest) {
        int rebelItemsFromRequestSize = countDistinctItemsInInventory(rebelItemsFromRequest);
        if (rebelItemsFromRequestSize < rebelItemsFromRequest.size()) {
            throw new DuplicateItemsInventoryException("Cannot pass duplicate items for trade.");
        }
    }

    private int countDistinctItemsInInventory(List<Item> items) {
        return (int) items.stream()
                .map(Item::getName)
                .distinct().count();
    }

    private boolean validateQuantityOfItemsInInventory(Rebel rebel, List<Item> rebelItemsFromRequest) {
        return rebelItemsFromRequest.stream()
                .allMatch((itemFromRequest) -> rebel.getInventory().getItems().stream()
                        .anyMatch((rebelItem) -> {
                            if (itemFromRequest.getName().equals(rebelItem.getName())) {
                                return rebelItem.getQuantity() >= itemFromRequest.getQuantity();
                            }
                            return false;
                        }));
    }

    private int getSumOfItemPoints(List<Item> items) {
        return items.stream()
                .mapToInt((item) -> item.getQuantity() * item.getName().value)
                .sum();
    }

    private void addTradeItems(List<Item> fullInventory, List<Item> itemsToAdd) {
        for (Item itemToTrade: itemsToAdd) {
            for (Item itemFullInventory: fullInventory) {
                if (itemFullInventory.getName().equals(itemToTrade.getName())) {
                    itemFullInventory.setQuantity(itemFullInventory.getQuantity() + itemToTrade.getQuantity());
                    break;
                }
            }
        }
    }

    private void removeTradeItems(List<Item> fullInventory, List<Item> itemsToRemove) {
        for (Item itemToTrade: itemsToRemove) {
            for (Item itemFullInventory: fullInventory) {
                if (itemFullInventory.getName().equals(itemToTrade.getName())) {
                    itemFullInventory.setQuantity(itemFullInventory.getQuantity() - itemToTrade.getQuantity());
                    break;
                }
            }
        }
    }
}
