package com.graysan.techservice.controller;

import com.graysan.techservice.model.SaleLog;
import com.graysan.techservice.repository.SaleLogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/saleLog")
public class SaleLogController {

    private final SaleLogRepository saleLogRepository;

    public SaleLogController(SaleLogRepository saleLogRepository) {
        this.saleLogRepository = saleLogRepository;
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyProduct(@RequestBody SaleLog saleLog) {
        try {
            boolean result = saleLogRepository.save(saleLog);
            if (result){
                return ResponseEntity.ok("Success buying product");
            }else{
                return ResponseEntity.internalServerError().body("Failed to buy product");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to buy product: " + e.getMessage());
        }
    }

}
