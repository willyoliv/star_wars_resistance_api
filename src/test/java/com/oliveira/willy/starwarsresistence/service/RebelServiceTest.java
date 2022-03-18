package com.oliveira.willy.starwarsresistence.service;

import com.oliveira.willy.starwarsresistence.exception.DuplicateItemsInventoryException;
import com.oliveira.willy.starwarsresistence.exception.InvalidReportException;
import com.oliveira.willy.starwarsresistence.exception.RebelNotFoundException;
import com.oliveira.willy.starwarsresistence.model.*;
import com.oliveira.willy.starwarsresistence.model.enums.Genre;
import com.oliveira.willy.starwarsresistence.model.enums.ItemInventory;
import com.oliveira.willy.starwarsresistence.repository.LocationRepository;
import com.oliveira.willy.starwarsresistence.repository.RebelRepository;
import com.oliveira.willy.starwarsresistence.repository.ReportRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(properties = {"maximumNumberOfReport = 3"})
@DisplayName("Rebel Service Test")
class RebelServiceTest {

    @InjectMocks
    private RebelService rebelService;

    @Mock
    private RebelRepository rebelRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private ReportRepository reportRepository;

    @Value("${maximumNumberOfReport}")
    private int maximumNumberOfReport;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        rebelService = new RebelService(rebelRepository, locationRepository, reportRepository);
    }

    @Test
    @DisplayName("Save rebel when successful")
    void saveRebel_WhenSuccessful() {
        Rebel rebelToBeSaved = this.createRebel(1L);

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
    @DisplayName("Save rebel throw DuplicateItemsInventoryException when the list of items has less than four items")
    void saveRebel_ThrowDuplicateItemsInventoryException() {
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
    void findRebelById_WhenSuccessful() {
        Rebel rebel = createRebel(1L);

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
    @DisplayName("Find Rebel by id throw RebelNotFoundException")
    void findRebelById_ThrowRebelNotFoundException() {

        Assertions.assertThatExceptionOfType(RebelNotFoundException.class)
                .isThrownBy(() -> this.rebelService.findRebelById(createRebel(1L).getId()))
                .withMessageContaining("Rebel ID 1 not found!");

    }

    @Test
    @DisplayName("Find All Rebels returns empty list when no rebel is found")
    void findAllRebels_ReturnsEmptyList_WhenRebelIsNotFound() {
        List<Rebel> rebels = this.rebelService.findAllRebels();

        Assertions.assertThat(rebels).isEmpty();
    }

    @Test
    @DisplayName("Find All Rebels returns list of rebels when successful")
    void findAllRebels_ReturnsListOfRebels_WhenSuccessful() {
        Rebel rebel = this.createRebel(1L);

        List<Rebel> rebels = List.of(rebel);

        Mockito.when(this.rebelService.findAllRebels()).thenReturn(rebels);

        List<Rebel> savedRebels = this.rebelService.findAllRebels();

        Assertions.assertThat(savedRebels).isNotEmpty().contains(rebel);
    }

    @Test
    @DisplayName("Update Rebel Location")
    void updateRebelLocation() {
        Rebel rebel = this.createRebel(1L);
        Location locationToBeSaved = Location.builder()
                .id(1L)
                .galaxyName("New Location")
                .latitude(54321L)
                .longitude(12345L)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(this.rebelRepository.findById(rebel.getId())).thenReturn(Optional.of(rebel));

        Mockito.when(this.locationRepository.save(locationToBeSaved)).thenReturn(locationToBeSaved);

        Location updatedLocation = this.rebelService.updateRebelLocation(rebel.getId(), locationToBeSaved);

        Assertions.assertThat(updatedLocation).isNotNull();
        Assertions.assertThat(updatedLocation.getGalaxyName()).isEqualTo("New Location");
        Assertions.assertThat(updatedLocation.getLatitude()).isEqualTo(54321L);
        Assertions.assertThat(updatedLocation.getLongitude()).isEqualTo(12345L);

    }

    @Test
    @DisplayName("Report Rebel Traitor when successful")
    void reportRebelTraitor_WhenSuccessful() {
        Rebel accuser = this.createRebel(1L);
        Rebel accused = this.createRebel(2L);

        this.rebelService.reportRebelTraitor(accuser, accused, "reason text");

        System.out.println(accused.getReport().size());
        Assertions.assertThat(accused.getReport()).isNotEmpty();
        Assertions.assertThat(accused.getReport().get(0).getReason()).isEqualTo("reason text");
        Assertions.assertThat(accused.getReport().get(0).getAccuser()).isEqualTo(accuser);

    }

    @Test
    @DisplayName("Report Rebel Traitor throw invalid report exception when the rebel tries to self-report")
    void reportRebelTraitor_ThrowInvalidReportException_WhenRebelTriesToSelfReport() {
        Rebel rebel = this.createRebel(1L);

        Assertions.assertThatExceptionOfType(InvalidReportException.class)
                .isThrownBy(() -> this.rebelService.reportRebelTraitor(rebel, rebel, "reason text"))
                .withMessageContaining("The rebel cannot self-report.");

    }

    @Test
    @DisplayName("Report Rebel Traitor throw invalid report exception when report already registered")
    void reportRebelTraitor_ThrowInvalidReportException_WhenReportAlreadyRegistered() {
        Rebel accuser = this.createRebel(1L);
        Rebel accused = this.createRebel(2L);

        Mockito.when(this.reportRepository.findByAccusedAndAccuser(accused, accuser)).thenReturn(Optional.ofNullable(createReport(accused, accuser)));

        Assertions.assertThatExceptionOfType(InvalidReportException.class)
                .isThrownBy(() -> this.rebelService.reportRebelTraitor(accuser, accused, "reason text"))
                .withMessageContaining("Report already registered. It is not possible to report this rebel again.");

    }

    @Test
    @DisplayName("Report Rebel Traitor throw invalid report exception when report already registered")
    void reportRebelTraitor_ThrowInvalidReportException_WhenRebe() {

        Rebel accuser = this.createRebel(1L);
        Rebel accused = this.createRebel(2L);
        Rebel rebel1 = this.createRebel(3L);
        Rebel rebel2 = this.createRebel(4L);

        accused.getReport().add(createReport(accused, rebel1));
        accused.getReport().add(createReport(accused, rebel2));

        this.rebelService.reportRebelTraitor(accuser, accused, "reason text");

        Assertions.assertThat(accused.getReport()).isNotEmpty();
        Assertions.assertThat(accused.getReport().size()).isEqualTo(maximumNumberOfReport);
        Assertions.assertThat(accused.getInventory().isBlocked()).isEqualTo(true);

    }



    private Rebel createRebel(Long id) {
        return Rebel.builder()
                .id(id)
                .name("Rebel")
                .age(20)
                .genre(Genre.MALE)
                .location(Location.builder()
                        .id(1L)
                        .galaxyName("Test")
                        .latitude(123123L)
                        .longitude(123123L)
                        .createdAt(LocalDateTime.now())
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
                .report(new ArrayList<>())
                .build();
    }

    private List<Item> createItemList() {
        return List.of(Item.builder().id(1L).name(ItemInventory.WEAPON).quantity(2).build(),
                Item.builder().id(2L).name(ItemInventory.WATER).quantity(2).build(),
                Item.builder().id(3L).name(ItemInventory.FOOD).quantity(2).build(),
                Item.builder().id(4L).name(ItemInventory.BULLET).quantity(2).build()
        );
    }

    private Report createReport(Rebel accused, Rebel accuser) {
        return Report.builder()
                .accused(accused)
                .accuser(accuser)
                .reason("reason text")
                .build();
    }

}






