package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.dto.ProductDTO;
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

    @Autowired
    private final CategoryService categoryService;

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

    public Page<Product> findFilteredProduct (
            String search,
            Long categoryId,
            Pageable pageable
    ) {
       return productRepository.findFilteredProducts(search, categoryId, pageable);
    }

    public void updateProduct(
            ProductDTO productDTO,
            long id
    ) {
        Product currentProduct = productRepository.findById(id).orElse(null);
        if (currentProduct == null) {
            return;
        }

        currentProduct.setName(productDTO.getName());
        currentProduct.setBrand(productDTO.getBrand());
        currentProduct.setModel(productDTO.getModel());
        currentProduct.setPromotion(productDTO.isPromotion());
        currentProduct.setDescription(productDTO.getDescription());
        currentProduct.setUrlPhoto(productDTO.getUrlPhoto());
        currentProduct.setInStock(productDTO.isInStock());
        currentProduct.setAvailable(productDTO.isAvailable());
        currentProduct.setPromotionPourcent(productDTO.getPromotionPourcent());
        currentProduct.setPrice(productDTO.getPrice());
        currentProduct.setStock(productDTO.getStock());
        currentProduct.setCategory(categoryService.getCategory(productDTO.getCategory()));

        productRepository.save(currentProduct);
    }

    public void createProduct(ProductDTO productDTO) {
        Product currentProduct = new Product();
        currentProduct.setName(productDTO.getName());
        currentProduct.setInStock(productDTO.isInStock());
        currentProduct.setAvailable(productDTO.isAvailable());
        currentProduct.setStock(productDTO.getStock());
        currentProduct.setPrice(productDTO.getPrice());

        currentProduct.setPromotion(productDTO.isPromotion());

        currentProduct.setBrand(productDTO.getBrand());
        currentProduct.setModel(productDTO.getModel());
        currentProduct.setUrlPhoto(productDTO.getUrlPhoto());
        currentProduct.setPromotionPourcent(productDTO.getPromotionPourcent());
        currentProduct.setDescription(productDTO.getDescription());

        Category category = categoryService.getCategory(productDTO.getCategory());
        if (category != null) {
            currentProduct.setCategory(category);
        }

        productRepository.save(currentProduct);

    }

    public void removeFromStock(int qty, Product product){
        product.setStock(product.getStock() - qty);
        productRepository.save(product);
    }
}
