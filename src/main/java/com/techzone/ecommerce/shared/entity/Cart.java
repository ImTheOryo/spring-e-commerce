package com.techzone.ecommerce.shared.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Cart extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartProduct> cartProductList;

    public double getTotal() {
        double total = 0;

        for (CartProduct cartProduct : cartProductList) {
            Product product = cartProduct.getProduct();
            double price = product.isPromotion() ? product.getPrice() * (1 - product.getPromotionPourcent() / 100.0) : product.getPrice();
            total += price * cartProduct.getQuantity();
        }
        return total;
    }

    public long getQty() {
        long qty = 0;

        for (CartProduct cartProduct : cartProductList) {
            Product product = cartProduct.getProduct();
            double price = product.isPromotion() ? product.getPrice() * (1 - product.getPromotionPourcent() / 100.0) : product.getPrice();
            qty += cartProduct.getQuantity();
        }

        return qty;
    }
}
