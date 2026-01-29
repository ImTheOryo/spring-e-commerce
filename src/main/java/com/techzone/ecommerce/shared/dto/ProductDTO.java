package com.techzone.ecommerce.shared.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private long category;
    private String description;

    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private int stock;

    @Min(value = 0, message = "Le prix ne peut pas être négatif")
    private double price;

    private String brand;
    private String model;
    private boolean available;
    private boolean inStock;
    private String urlPhoto;

    @Min(value = 0, message = "La remise ne peut pas être négative")
    @Max(value = 100, message = "La remise ne peut pas dépasser 100%")
    private byte promotionPourcent;

    private boolean promotion;
}