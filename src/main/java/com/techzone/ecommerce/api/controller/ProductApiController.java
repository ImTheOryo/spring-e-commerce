package com.techzone.ecommerce.api.controller;

import com.techzone.ecommerce.shared.entity.Category;
import com.techzone.ecommerce.shared.entity.Product;
import com.techzone.ecommerce.shared.service.CategoryService;
import com.techzone.ecommerce.shared.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductApiController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> productPage = productService.getAll();

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.get(id);

        if (product == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductPerCategory(@PathVariable Long categoryId) {
        Category category = categoryService.getCategory(categoryId);
        List<Product> productPage = productService.getAllAvailableByCategory(category);

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> getProductPerSearch(@PageableDefault(size = 12) Pageable pageable, @RequestParam("query") String search) {
        Page<Product> productPage = productService.getSearch(pageable, search);

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    @GetMapping("/promo")
    public ResponseEntity<Page<Product>> getProductPerPromo(@PageableDefault(size = 12) Pageable pageable) {
        Page<Product> productPage = productService.getPromo(pageable);

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    @GetMapping("/in-stock")
    public ResponseEntity<Page<Product>> getProductPerStock(@PageableDefault(size = 12) Pageable pageable) {
        Page<Product> productPage = productService.getInStock(pageable);

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }
}
