package com.techzone.ecommerce.shared.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;

@Getter
@Setter
@Entity
public class OrderProduct extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;
    private byte promotionPourcent;
    private boolean isPromotion;
    private double price;

    @PrePersist
    public void calculatePrice() {
        DecimalFormat df = new DecimalFormat("#.00");

        if (product != null) {
            double basePrice = product.getPrice();
            if (isPromotion && promotionPourcent > 0) {
                this.price = Double.parseDouble(df.format(basePrice * (1 - this.promotionPourcent / 100.0)));
            } else {
                this.price = Double.parseDouble(df.format(basePrice));
            }
        }
    }
}
