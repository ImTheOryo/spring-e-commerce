package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.entity.Product;
import com.techzone.ecommerce.shared.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public int getProductsCount() {
        return productRepository.findAll().size();
    }

    public Page<Product> products(Pageable pageable, String search) {
        return productRepository.searchProduct(pageable, search);
    }

}
