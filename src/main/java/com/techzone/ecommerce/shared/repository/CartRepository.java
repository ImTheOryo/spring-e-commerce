package com.techzone.ecommerce.shared.repository;

import com.techzone.ecommerce.shared.repository.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}