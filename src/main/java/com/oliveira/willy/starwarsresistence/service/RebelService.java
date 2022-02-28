package com.oliveira.willy.starwarsresistence.service;

import com.oliveira.willy.starwarsresistence.model.Rebel;
import com.oliveira.willy.starwarsresistence.repository.RebelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RebelService {
    private final RebelRepository rebelRepository;

    public List<Rebel> findAll() {
        return rebelRepository.findAll();
    }

    public Rebel saveRebel(Rebel rebel) {
        rebel.getInventory().setInventoryToItem();
        return rebelRepository.save(rebel);
    }
}
