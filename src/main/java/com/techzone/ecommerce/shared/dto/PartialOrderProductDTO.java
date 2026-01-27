package com.techzone.ecommerce.shared.dto;

import com.techzone.ecommerce.shared.entity.OrderProduct;
import com.techzone.ecommerce.shared.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartialOrderProductDTO {
    private Product product;
    private int quantity;
    private byte promotionPourcent;
    private boolean isPromotion;
    private double price;

    PartialOrderProductDTO(OrderProduct orderProduct){
        this.product = orderProduct.getProduct();
        this.quantity = orderProduct.getQuantity();
        this.promotionPourcent = orderProduct.getPromotionPourcent();
        this.isPromotion = orderProduct.isPromotion();
        this.price = orderProduct.getPrice();
    }
}
