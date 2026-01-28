package com.techzone.ecommerce.app.controller;

import com.techzone.ecommerce.shared.entity.Category;
import com.techzone.ecommerce.shared.entity.Product;
import com.techzone.ecommerce.shared.service.CategoryService;
import com.techzone.ecommerce.shared.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model, @PageableDefault(size = 12) Pageable pageable) {
        Page<Product> productPage = productService.getAllAvailable(pageable);
        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("categories", categories);
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
        Product product = productService.get(id);
        List<Category> categories = categoryService.getAllCategory();

        if (product == null) {
            return "product/home";
        }

        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "product/product";
    }

    @GetMapping("/category/{id}")
    public String getProductPerCategoryView(
            Model model,
            @PageableDefault(size = 12) Pageable pageable,
            @PathVariable("id") Long categoryId
    ) {
        Category category = categoryService.getCategory(categoryId);
        Page<Product> productPage = productService.getAllAvailableByCategory(pageable, category);
        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("categories", categories);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("hasNext", productPage.hasNext());
        model.addAttribute("hasPrevious", productPage.hasPrevious());
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("category", category);
        return "product/category";
    }

    @GetMapping("/search")
    public String getProductPerSearchView(
            Model model,
            @PageableDefault(size = 12) Pageable pageable,
            @RequestParam("query") String search
    ) {
        Page<Product> productPage = productService.getSearch(pageable, search);
        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("categories", categories);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("search", search);
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("hasNext", productPage.hasNext());
        model.addAttribute("hasPrevious", productPage.hasPrevious());
        model.addAttribute("pageSize", pageable.getPageSize());

        return "product/search";
    }

    @GetMapping("/promo")
    public String getProductPerPromoView(
            Model model,
            @PageableDefault(size = 12) Pageable pageable
    ) {
        Page<Product> productPage = productService.getPromo(pageable);
        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("categories", categories);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("hasNext", productPage.hasNext());
        model.addAttribute("hasPrevious", productPage.hasPrevious());
        model.addAttribute("pageSize", pageable.getPageSize());

        return "product/promotion";
    }

    @GetMapping("/in-stock")
    public String getProductPerStockView(
            Model model,
            @PageableDefault(size = 12) Pageable pageable
    ) {
        Page<Product> productPage = productService.getInStock(pageable);
        List<Category> categories = categoryService.getAllCategory();

        model.addAttribute("categories", categories);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("hasNext", productPage.hasNext());
        model.addAttribute("hasPrevious", productPage.hasPrevious());
        model.addAttribute("pageSize", pageable.getPageSize());

        return "product/inStock";
    }
}
