package com.oliveira.willy.starwarsresistence.controller;

import com.oliveira.willy.starwarsresistence.model.Rebel;
import com.oliveira.willy.starwarsresistence.service.RebelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("rebels")
public class RebelController {
    private final RebelService rebelService;

    @GetMapping
    private ResponseEntity<List<Rebel>> findAll() {
        return new ResponseEntity<>(rebelService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Rebel> saveRebel(@RequestBody Rebel rebel) {
        return new ResponseEntity<>(rebelService.saveRebel(rebel), HttpStatus.CREATED);
    }
}
