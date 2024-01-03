package com.graysan.techservice.controller;

import com.graysan.techservice.model.Product;
import com.graysan.techservice.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productRepository.getAll());
    }

}
