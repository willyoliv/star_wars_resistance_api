package com.oliveira.willy.starwarsresistence.controller;

import com.oliveira.willy.starwarsresistence.dto.*;
import com.oliveira.willy.starwarsresistence.mapper.ItemMapper;
import com.oliveira.willy.starwarsresistence.mapper.LocationMapper;
import com.oliveira.willy.starwarsresistence.mapper.RebelMapper;
import com.oliveira.willy.starwarsresistence.model.Item;
import com.oliveira.willy.starwarsresistence.model.Location;
import com.oliveira.willy.starwarsresistence.model.Rebel;
import com.oliveira.willy.starwarsresistence.service.RebelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("rebels")
public class RebelController {
    private final RebelService rebelService;

    private final RebelMapper rebelMapper;

    private final LocationMapper locationMapper;

    private final ItemMapper itemMapper;

    @GetMapping
    private ResponseEntity<List<Rebel>> findAll() {
        return new ResponseEntity<>(rebelService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Rebel> saveRebel(@Valid @RequestBody RebelCreateDto rebelCreateDto) {
        Rebel rebel = rebelMapper.rebelDTOToRebel(rebelCreateDto);
        return new ResponseEntity<>(rebelService.saveRebel(rebel), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{rebelId}/update-location")
    private ResponseEntity<SuccessMessage> updateRebelLocation(@PathVariable("rebelId") Long rebelId,
                                                               @Valid @RequestBody LocationDto locationDto) {
        Location location = locationMapper.locationDTOToLocation(locationDto);
        rebelService.updateRebelLocation(rebelId, location);
        return new ResponseEntity<>(new SuccessMessage("Location updated successfully"), HttpStatus.OK);
    }

    @PostMapping(path = "/{accuserId}/report-traitor")
    private ResponseEntity<SuccessMessage> reportRebelTraitor(@PathVariable("accuserId") Long accuserId,
                                                              @Valid @RequestBody ReportCreateDto reportCreateDto) {
        rebelService.reportRebelTraitor(accuserId, reportCreateDto.getAccusedId(), reportCreateDto.getReason());
        return new ResponseEntity<>(new SuccessMessage("Report made successfully"), HttpStatus.OK);
    }

    @PostMapping(path = "/trade")
    public ResponseEntity<SuccessMessage> tradeItemsBetweemRebels(@Valid @RequestBody TradeDto tradeDto) {
        Rebel fromRebel = rebelService.findRebelById(tradeDto.getFromRebel().getRebelId());
        Rebel toRebel = rebelService.findRebelById(tradeDto.getToRebel().getRebelId());
        List<Item> fromRebelItems = tradeDto.getFromRebel().getItems().stream()
                .map(itemMapper::itemDTOToItem).collect(Collectors.toList());
        List<Item> toRebelItems = tradeDto.getToRebel().getItems().stream()
                .map(itemMapper::itemDTOToItem).collect(Collectors.toList());
        rebelService.trade(fromRebel, toRebel, fromRebelItems, toRebelItems);
        return new ResponseEntity<>(new SuccessMessage("Trade made successfully"), HttpStatus.OK);
    }
}
