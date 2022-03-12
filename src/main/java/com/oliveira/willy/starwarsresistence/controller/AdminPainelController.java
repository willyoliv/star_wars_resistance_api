package com.oliveira.willy.starwarsresistence.controller;

import com.oliveira.willy.starwarsresistence.service.AdminPainelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin-painel")
public class AdminPainelController {

    private final AdminPainelService adminPainelService;

    @GetMapping(path = "/issue-report")
    public void issueReport() {
        adminPainelService.issueReport();
    }
}
