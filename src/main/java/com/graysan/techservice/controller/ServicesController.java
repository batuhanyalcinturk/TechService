package com.graysan.techservice.controller;

import com.graysan.techservice.model.Services;
import com.graysan.techservice.repository.ServicesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServicesController {

    private final ServicesRepository servicesRepository;

    public ServicesController(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Services>> getAll(){
        return ResponseEntity.ok(servicesRepository.getAll());
    }
}
