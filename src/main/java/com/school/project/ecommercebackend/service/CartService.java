package com.school.project.ecommercebackend.service;

import com.school.project.ecommercebackend.api.model.CartRequest;
import com.school.project.ecommercebackend.exception.ProductNotFoundException;
import com.school.project.ecommercebackend.model.Cart;
import com.school.project.ecommercebackend.model.LocalUser;
import com.school.project.ecommercebackend.model.Product;
import com.school.project.ecommercebackend.model.dao.CartItemDAO;
import com.school.project.ecommercebackend.model.dao.ProductsDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    private final ProductsDAO productsDAO;
    private final CartItemDAO cartItemDAO;

    public CartService(ProductsDAO productsDAO, CartItemDAO cartItemDAO) {
        this.productsDAO = productsDAO;
        this.cartItemDAO = cartItemDAO;
    }

    public Cart addToCart(CartRequest cartRequest, LocalUser user) throws ProductNotFoundException {
        Optional<Product> opProduct = productsDAO.findById(cartRequest.getProductId());
        if(opProduct.isEmpty()){
            throw new ProductNotFoundException();
        }
        Product product = opProduct.get();

        Cart cart = new Cart();
        cart.setPrice(cartRequest.getPrice());
        cart.setQuantity(cartRequest.getQuantity());
        cart.setProduct(product);
        cart.setLocalUser(user);
        return cartItemDAO.save(cart);
    }
}

