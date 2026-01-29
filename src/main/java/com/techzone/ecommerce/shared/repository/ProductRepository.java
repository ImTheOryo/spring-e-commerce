package com.techzone.ecommerce.shared.repository;

import com.techzone.ecommerce.shared.entity.Category;
import com.techzone.ecommerce.shared.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByIsAvailableTrue(Pageable pageable);

    Page<Product> findAllByIsAvailableTrueAndCategory(Pageable pageable, Category category);

    List<Product> findAllByIsAvailableTrueAndCategory(Category category);


    Page<Product> findAllByIsAvailableTrueAndIsPromotionTrue(Pageable pageable);

    Page<Product> findAllByIsAvailableTrueAndIsInStockTrue(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.model) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Product> searchProduct(Pageable pageable, String search);

    @Query("""
                SELECT p FROM Product p WHERE
                (:categoryId IS NULL OR p.category.id = :categoryId) AND
                (:search IS NULL OR 
                LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR 
                LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')) OR 
                LOWER(p.model) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<Product> findFilteredProducts(
            @Param("search") String search,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

    @Query("""
                SELECT p FROM Product p WHERE
                (:categoryId IS NULL OR p.category.id = :categoryId) AND
                (:search IS NULL OR 
                LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR 
                LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%')) OR 
                LOWER(p.model) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    List<Product> findFilteredProducts(@Param("search") String search, @Param("categoryId") Long categoryId);
}
