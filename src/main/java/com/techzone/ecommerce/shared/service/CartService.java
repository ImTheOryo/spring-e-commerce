package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.entity.Cart;
import com.techzone.ecommerce.shared.entity.CartProduct;
import com.techzone.ecommerce.shared.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public Cart addCartProduct(CartProduct cartProduct, Cart cart) {
        try {
            cartProduct.setCart(cart);

            List<CartProduct> list = cart.getCartProductList();
            list.add(cartProduct);
            cart.setCartProductList(list);
            return cartRepository.save(cart);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
