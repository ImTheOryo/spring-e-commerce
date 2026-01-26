package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.entity.Category;
import com.techzone.ecommerce.shared.entity.Product;
import com.techzone.ecommerce.shared.repository.ProductRepository;
import com.techzone.ecommerce.shared.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model, @PageableDefault(size = 12) Pageable pageable) {
        Page<Product> productPage = productRepository.findAllByIsAvailableTrue(pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("hasNext", productPage.hasNext());
        model.addAttribute("hasPrevious", productPage.hasPrevious());
        model.addAttribute("pageSize", pageable.getPageSize());

        return "product/home";
    }

    @GetMapping("/product/{id}")
    public String getProduct(Model model, @PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);

        if(product.isEmpty()){
            return "product/home";
        }
        model.addAttribute("product", product.get());
        return "product/product";
    }

    @GetMapping("/category/{id}")
    public String getProductPerCategory(
            Model model,
            @PageableDefault(size =12) Pageable pageable,
            @PathVariable("id") Long categoryId
    ){
        Category category = categoryService.getCategory(categoryId);
        Page<Product> productPage = productRepository.findAllByIsAvailableTrueAndCategory(pageable, category);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("hasNext", productPage.hasNext());
        model.addAttribute("hasPrevious", productPage.hasPrevious());
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("category", category);
        //TODO regler le probleme de pagination (ça bug au 1er chargelent de category)
        return "product/category";
    }
}
