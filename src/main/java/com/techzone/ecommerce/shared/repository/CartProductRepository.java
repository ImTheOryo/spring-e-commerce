package com.techzone.ecommerce.shared.repository;

import com.techzone.ecommerce.shared.entity.CartProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
//    Page<CartProduct> findAllByIsAvailableTrue(Pageable pageable);
}
