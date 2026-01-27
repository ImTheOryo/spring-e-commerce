package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public int getProductsCount() {
        return productRepository.findAll().size();
    }

}
