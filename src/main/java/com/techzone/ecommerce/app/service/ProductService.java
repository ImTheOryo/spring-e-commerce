package com.techzone.ecommerce.app.service;

import com.techzone.ecommerce.shared.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Model getAll(Model model){
        model.addAttribute("products", productRepository.findAll());
        return model;
    }
}
