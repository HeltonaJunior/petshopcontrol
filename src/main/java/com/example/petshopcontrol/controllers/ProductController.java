package com.example.petshopcontrol.controllers;

import com.example.petshopcontrol.dtos.ProductRecordDto;
import com.example.petshopcontrol.models.ProductModel;
import com.example.petshopcontrol.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value="id") UUID id){
        Optional<ProductModel> product0 = productRepository.findById(id);
        if (product0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id,
                                                @RequestBody @Valid ProductRecordDto productRecordDto){
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = product0.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct (@PathVariable(value="id") UUID id) {
        Optional<ProductModel> product0 = productRepository.findById(id);
        if(product0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(product0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }
}
