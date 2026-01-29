package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.entity.Category;
import com.techzone.ecommerce.shared.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getCategory(Long id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);

            if (category.isPresent()) {
                return category.get();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public List<Category> getAllCategory(){
        try {
            List<Category> categories = categoryRepository.findAll();

            if (!categories.isEmpty()) {
                return categories;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public boolean createCategory(String categoryName) {
        try {
            Category category = new Category();
            category.setName(categoryName);
            categoryRepository.save(category);
            return true;
        }  catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    public boolean updateCategory(Long id, String categoryName) {
        try {
            Category currentCategory = categoryRepository.findById(id).get();
            currentCategory.setName(categoryName);
            categoryRepository.save(currentCategory);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }
}
