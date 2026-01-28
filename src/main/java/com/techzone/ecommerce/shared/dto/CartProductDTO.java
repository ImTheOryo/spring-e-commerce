package com.techzone.ecommerce.shared.dto;

import com.techzone.ecommerce.shared.entity.CartProduct;
import com.techzone.ecommerce.shared.entity.OrderProduct;
import com.techzone.ecommerce.shared.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductDTO {
    private Product product;
    private int quantity;

    CartProductDTO(CartProduct cartProduct){
        this.product = cartProduct.getProduct();
        this.quantity = cartProduct.getQuantity();
    }
}
