package com.oliveira.willy.starwarsresistence.service;

import com.oliveira.willy.starwarsresistence.dto.ReportDto;
import com.oliveira.willy.starwarsresistence.exception.RebelNotFoundException;
import com.oliveira.willy.starwarsresistence.model.Localization;
import com.oliveira.willy.starwarsresistence.model.Rebel;
import com.oliveira.willy.starwarsresistence.model.Report;
import com.oliveira.willy.starwarsresistence.repository.LocalizationRepository;
import com.oliveira.willy.starwarsresistence.repository.RebelRepository;
import com.oliveira.willy.starwarsresistence.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RebelService {
    private final RebelRepository rebelRepository;

    private final ReportRepository reportRepository;

    public List<Rebel> findAll() {
        return rebelRepository.findAll();
    }

    @Transactional
    public Rebel saveRebel(Rebel rebel) {
        rebel.getInventory().setInventoryToItem();
        return rebelRepository.save(rebel);
    }

    public Rebel findRebelById(Long id) {
        return rebelRepository.findById(id).orElseThrow(() -> new RebelNotFoundException("Rebel " + id + " not found!"));
    }

    public void updateRebelLocation(Long rebelId, Localization localization) {
        Rebel rebel = findRebelById(rebelId);
        localization.setId(rebel.getLocalization().getId());
        localization.setCreatedAt(rebel.getLocalization().getCreatedAt());
        rebel.setLocalization(localization);
        rebelRepository.save(rebel);
    }

    public void reportRebelTraitor(Long accuserId, Long accusedId ,String reason) {
        Rebel acusser = findRebelById(accuserId);
        Rebel accused = findRebelById(accusedId);
        Report report = Report.builder()
                .accuser(acusser)
                .accused(accused)
                .reason(reason)
                .build();
        accused.getReport().add(report);
        rebelRepository.save(accused);
    }
}
