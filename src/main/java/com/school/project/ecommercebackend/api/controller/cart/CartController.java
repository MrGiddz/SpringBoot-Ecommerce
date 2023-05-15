package com.school.project.ecommercebackend.api.controller.cart;

import com.school.project.ecommercebackend.api.model.CartRequest;
import com.school.project.ecommercebackend.exception.ProductNotFoundException;
import com.school.project.ecommercebackend.exception.UserAlreadyExistsException;
import com.school.project.ecommercebackend.model.Cart;
import com.school.project.ecommercebackend.model.LocalUser;
import com.school.project.ecommercebackend.model.dao.CartItemDAO;
import com.school.project.ecommercebackend.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartItemDAO cartItemDAO;
    private CartService cartService;

    public CartController(CartService cartService, CartItemDAO cartItemDAO, CartService cartService1) {
        this.cartItemDAO = cartItemDAO;
        this.cartService = cartService1;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<Cart>> getCartItems(@AuthenticationPrincipal LocalUser user, @PathVariable Long userId){
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(cartItemDAO.findByLocalUser_Id(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Cart> putAddress(
            @AuthenticationPrincipal LocalUser user, @PathVariable Long userId,
            @RequestBody CartRequest cartRequest) {
        if (!userHasPermission(user, userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            cartService.addToCart(cartRequest, user);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();

    }
    /**
     * Method to check if an authenticated user has permission to a user ID.
     * @param user The authenticated user.
     * @param id The user ID.
     * @return True if they have permission, false otherwise.
     */
    private boolean userHasPermission(LocalUser user, Long id) {
        return user.getId() == id;
    }
}
