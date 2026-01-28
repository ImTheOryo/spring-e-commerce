package com.techzone.ecommerce.shared.dto;

import com.techzone.ecommerce.shared.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartDTO {
    private OrderStatus status;
    private double price;
    private long qty;
    private LocalDateTime createdAt;
    private long id;
    private String firstname;
    private String lastname;
    private String address;
    private String phone;
    private List<CartProductDTO> cartProductDTOS = new ArrayList<>();

    public CartDTO(Cart cart) {
        this.price = cart.getTotal();
        this.createdAt = cart.getCreatedAt();
        this.id = cart.getId();

        for (CartProduct cartProduct : cart.getCartProductList()) {
            cartProductDTOS.add(new CartProductDTO(cartProduct));
        }
    }
}
