package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.entity.Cart;
import com.techzone.ecommerce.shared.entity.CartProduct;
import com.techzone.ecommerce.shared.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public Cart addCartProduct(CartProduct cartProduct, Cart cart) {
        try {
            cartProduct.setCart(cart);

            List<CartProduct> list = cart.getCartProductList();

            if (list.stream().anyMatch(cP -> Objects.equals(cP.getProduct().getId(), cartProduct.getProduct().getId()))) {
                CartProduct cartProductCart = list.stream().filter(cP -> Objects.equals(cP.getProduct().getId(), cartProduct.getProduct().getId())).findFirst().get();
                int quantity = cartProduct.getQuantity() + cartProductCart.getQuantity();

                cartProductCart.setQuantity(Math.min(quantity, cartProductCart.getProduct().getStock()));
            }else{
                list.add(cartProduct);
            }

            cart.setCartProductList(list);
            return cartRepository.save(cart);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
