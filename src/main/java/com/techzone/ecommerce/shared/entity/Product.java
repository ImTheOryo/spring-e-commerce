package com.techzone.ecommerce.shared.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String name;
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
}
