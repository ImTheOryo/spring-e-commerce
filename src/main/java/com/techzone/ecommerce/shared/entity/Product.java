package com.techzone.ecommerce.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Product extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String name;

    @Column(length=512)
    private String description;
    private int stock;
    private double price;
    private String brand;
    private String model;
    private boolean isAvailable;
    private boolean isInStock;
    private String urlPhoto;
    private byte promotionPourcent;
    private boolean isPromotion;

    public void setStock(int stock) {
        this.stock = Math.max(stock, 0);
    }

    public void setPrice(double price) {
        this.price = Math.max(price, 0);
    }

    public void setPromotionPourcent(byte promotionPourcent) {
        this.promotionPourcent = (byte) Math.min(100, Math.max(0, promotionPourcent));
    }
}
