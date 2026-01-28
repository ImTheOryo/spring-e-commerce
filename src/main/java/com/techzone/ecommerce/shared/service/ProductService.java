package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.entity.Category;
import com.techzone.ecommerce.shared.entity.Product;
import com.techzone.ecommerce.shared.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public int getProductsCount() {

        return productRepository.findAll().size();
    }

    public Page<Product> getAllAvailable(Pageable pageable) {
        return productRepository.findAllByIsAvailableTrue(pageable);
    }

    public Product get(long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public Page<Product> getAllAvailableByCategory(Pageable pageable, Category category) {
        return productRepository.findAllByIsAvailableTrueAndCategory(pageable, category);
    }

    public Page<Product> getSearch(Pageable pageable, String search) {
        return productRepository.searchProduct(pageable, search);
    }

    public Page<Product> getPromo(Pageable pageable) {
        return productRepository.findAllByIsAvailableTrueAndIsPromotionTrue(pageable);
    }

    public Page<Product> getInStock(Pageable pageable) {
        return productRepository.findAllByIsAvailableTrueAndIsInStockTrue(pageable);
    }
}
