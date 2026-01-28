package com.techzone.ecommerce.shared.service;

import com.techzone.ecommerce.shared.entity.Cart;
import com.techzone.ecommerce.shared.entity.CartProduct;
import com.techzone.ecommerce.shared.repository.CartProductRepository;
import com.techzone.ecommerce.shared.repository.CartRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    public Cart addCartProduct(CartProduct cartProduct, Cart cart) {
        try {
            cartProduct.setCart(cart);

            List<CartProduct> list = cart.getCartProductList();

            if (list.stream().anyMatch(cP -> Objects.equals(cP.getProduct().getId(), cartProduct.getProduct().getId()))) {
                CartProduct cartProductCart = list.stream().filter(cP -> Objects.equals(cP.getProduct().getId(), cartProduct.getProduct().getId())).findFirst().get();
                int quantity = cartProduct.getQuantity() + cartProductCart.getQuantity();

                cartProductCart.setQuantity(Math.min(quantity, cartProductCart.getProduct().getStock()));
            } else {
                list.add(cartProduct);
            }

            cart.setCartProductList(list);
            return cartRepository.save(cart);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public CartProduct updateCartProduct(CartProduct cartProduct, Cart cart) {
        try {
            Optional<CartProduct> cartProductToUpDate = Optional.of(cart.getCartProductList().stream().filter(cP -> cP.getProduct().getId() == cartProduct.getProduct().getId()).findFirst().get());
            CartProduct finalCartProduct = cartProductToUpDate.get();
            finalCartProduct.setQuantity(cartProduct.getQuantity());
            return cartProductRepository.save(finalCartProduct);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public boolean removeCartProduct(long id, Cart cart) {
        try {
            CartProduct cartProduct = cart.getCartProductList().stream().filter(cP -> cP.getProduct().getId() == id).findFirst().get();
            cart.getCartProductList().removeIf(cP -> cP.getProduct().getId() == id);
            cartRepository.save(cart);
            cartProductRepository.delete(cartProduct);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }
}
