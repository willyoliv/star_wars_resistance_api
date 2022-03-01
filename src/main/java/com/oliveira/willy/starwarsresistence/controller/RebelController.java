package com.oliveira.willy.starwarsresistence.controller;

import com.oliveira.willy.starwarsresistence.dto.LocalizationDto;
import com.oliveira.willy.starwarsresistence.dto.RebelCreateDto;
import com.oliveira.willy.starwarsresistence.mapper.LocalizationMapper;
import com.oliveira.willy.starwarsresistence.mapper.RebelMapper;
import com.oliveira.willy.starwarsresistence.model.Localization;
import com.oliveira.willy.starwarsresistence.model.Rebel;
import com.oliveira.willy.starwarsresistence.service.RebelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("rebels")
public class RebelController {
    private final RebelService rebelService;

    private final RebelMapper rebelMapper;

    private final LocalizationMapper localizationMapper;

    @GetMapping
    private ResponseEntity<List<Rebel>> findAll() {
        return new ResponseEntity<>(rebelService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Rebel> saveRebel(@Valid @RequestBody RebelCreateDto rebelCreateDto) {
        Rebel rebel = rebelMapper.rebelDTOToRebel(rebelCreateDto);
        return new ResponseEntity<>(rebelService.saveRebel(rebel), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{rebelId}/update_localization")
    private ResponseEntity<Void> updateRebelLocation(@PathVariable(value = "rebelId") Long rebelId, @Valid @RequestBody LocalizationDto localizationDto) {
        Localization localization = localizationMapper.localizationDTOToLocalization(localizationDto);
        rebelService.updateRebelLocation(rebelId, localization);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
