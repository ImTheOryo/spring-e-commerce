package com.techzone.ecommerce.shared.repository;

import com.techzone.ecommerce.shared.entity.Category;
import com.techzone.ecommerce.shared.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByIsAvailableTrue(Pageable pageable);

    Page<Product> findAllByIsAvailableTrueAndCategory(Pageable pageable, Category category);
    Page<Product> findAllByIsAvailableTrueAndIsPromotionTrue(Pageable pageable);
    Page<Product> findAllByIsAvailableTrueAndStockGreaterThan(Pageable pageable, int stock);

    @Query("SELECT p FROM Product p WHERE LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.model) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Product> searchProduct(Pageable pageable, String search);

}
