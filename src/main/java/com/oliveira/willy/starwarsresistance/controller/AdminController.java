package com.oliveira.willy.starwarsresistance.controller;

import com.oliveira.willy.starwarsresistance.dto.AdminReport;
import com.oliveira.willy.starwarsresistance.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping(path = "/report")
    public ResponseEntity<AdminReport> report() {
        return new ResponseEntity<>(adminService.report(), HttpStatus.OK);
    }
}
