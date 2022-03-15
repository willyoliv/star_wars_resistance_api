package com.oliveira.willy.starwarsresistence.service;

import com.oliveira.willy.starwarsresistence.exception.DuplicateItemsInventoryException;
import com.oliveira.willy.starwarsresistence.exception.RebelNotFoundException;
import com.oliveira.willy.starwarsresistence.model.Inventory;
import com.oliveira.willy.starwarsresistence.model.Item;
import com.oliveira.willy.starwarsresistence.model.Location;
import com.oliveira.willy.starwarsresistence.model.Rebel;
import com.oliveira.willy.starwarsresistence.model.enums.Genre;
import com.oliveira.willy.starwarsresistence.model.enums.ItemInventory;
import com.oliveira.willy.starwarsresistence.repository.RebelRepository;
import com.oliveira.willy.starwarsresistence.repository.ReportRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@DisplayName("Rebel Service Test")
class RebelServiceTest {

    @InjectMocks
    private RebelService rebelService;

    @Mock
    private RebelRepository rebelRepository;

    @Mock
    private ReportRepository reportRepository;

    @BeforeEach
    void init(){
        rebelService = new RebelService(rebelRepository, reportRepository);
    }

    @Test
    @DisplayName("Save rebel when successful")
    void save_PersistRebel_whenSuccessful() {
        Rebel rebelToBeSaved = this.createRebel();

        Mockito.when(rebelService.saveRebel(rebelToBeSaved)).thenReturn(rebelToBeSaved);

        Rebel rebel = rebelService.saveRebel(rebelToBeSaved);
        Assertions.assertThat(rebel).isNotNull();
        Assertions.assertThat(rebel.getName()).isEqualTo("Rebel");
        Assertions.assertThat(rebel.getAge()).isEqualTo(20);
        Assertions.assertThat(rebel.getGenre()).isEqualTo(Genre.MALE);
        Assertions.assertThat(rebel.getLocation().getGalaxyName()).isEqualTo("Test");
        Assertions.assertThat(rebel.getLocation().getLatitude()).isEqualTo(123123L);
        Assertions.assertThat(rebel.getLocation().getLongitude()).isEqualTo(123123L);
    }

    @Test
    @DisplayName("Save rebel thow DuplicateItemsInventoryException when the list of items has less than four items")
    void save_ThrowDuplicateItemsInventoryException() {
        Rebel rebel = new Rebel();
        Inventory inventory = new Inventory();
        inventory.setItems(new ArrayList<>());
        rebel.setInventory(inventory);

        Assertions.assertThatExceptionOfType(DuplicateItemsInventoryException.class)
                .isThrownBy(() -> this.rebelService.saveRebel(rebel))
                .withMessageContaining("There are duplicate items in the inventory");
    }

    @Test
    @DisplayName("Find Rebel by id with successful")
    void finbById_whenSuccessful() {
        Rebel rebel = createRebel();

        Mockito.when(this.rebelRepository.findById(rebel.getId())).thenReturn(Optional.of(rebel));

        Rebel rebelFound = this.rebelService.findRebelById(1L);
        Assertions.assertThat(rebelFound).isNotNull();
        Assertions.assertThat(rebelFound.getName()).isEqualTo("Rebel");
        Assertions.assertThat(rebelFound.getAge()).isEqualTo(20);
        Assertions.assertThat(rebelFound.getGenre()).isEqualTo(Genre.MALE);
        Assertions.assertThat(rebelFound.getLocation().getGalaxyName()).isEqualTo("Test");
        Assertions.assertThat(rebelFound.getLocation().getLatitude()).isEqualTo(123123L);
        Assertions.assertThat(rebelFound.getLocation().getLongitude()).isEqualTo(123123L);
    }

    @Test
    @DisplayName("Find Rebel thow RebelNotFoundException")
    void finbById_ThowRebelNotFoundException() {

        Assertions.assertThatExceptionOfType(RebelNotFoundException.class)
                .isThrownBy(() -> this.rebelService.findRebelById(createRebel().getId()))
                .withMessageContaining("Rebel ID 1 not found!");

    }

    @Test
    @DisplayName("Find All returns empty list when no rebel is found")
    void findAll_ReturnsEmptyList_WhenRebelIsNotFound() {
        List<Rebel> rebels = this.rebelService.findAll();

        Assertions.assertThat(rebels).isEmpty();
    }

    @Test
    @DisplayName("Find All returns list of rebels when successful")
    void findAll_ReturnsListOfRebels_WhenSuccessful() {
        Rebel rebel = this.createRebel();

        List<Rebel> rebels = List.of(rebel);

        Mockito.when(this.rebelService.findAll()).thenReturn(rebels);

        List<Rebel> savedRebels = this.rebelService.findAll();

        Assertions.assertThat(savedRebels).isNotEmpty().contains(rebel);
    }

    @Test
    @DisplayName("Update location")
    void updateRebelLocation() {
//        Rebel rebel = Rebel.builder().name("Willy");
//        Rebel rebel = this.createRebel();
//        Location location = rebel.getLocation();
//        location.setGalaxyName("New Location");
//        location.setLatitude(54321L);
//        location.setLongitude(12345L);
//
//        Mockito.when(this.rebelRepository.findById(rebel.getId())).thenReturn(Optional.of(rebel));
//
//        this.rebelService.updateRebelLocation(rebel.getId(), rebel.getLocation());
//
//        Mockito.verify(this.rebelRepository.save(rebel), Mockito.times(1));
    }


    private Rebel createRebel() {
        return Rebel.builder()
                .id(1L)
                .name("Rebel")
                .age(20)
                .genre(Genre.MALE)
                .location(Location.builder()
                        .id(1L)
                        .galaxyName("Test")
                        .latitude(123123L)
                        .longitude(123123L)
                        .build())
                .inventory(Inventory.builder()
                        .id(1L)
                        .isBlocked(false)
                        .items(createItemList())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(null)
                        .build())
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .report(null)
                .build();
    }

    private List<Item> createItemList() {
        return List.of(Item.builder().id(1L).name(ItemInventory.WEAPON).quantity(2).build(),
                Item.builder().id(2L).name(ItemInventory.WATER).quantity(2).build(),
                Item.builder().id(3L).name(ItemInventory.FOOD).quantity(2).build(),
                Item.builder().id(4L).name(ItemInventory.BULLET).quantity(2).build()
        );
    }

}






