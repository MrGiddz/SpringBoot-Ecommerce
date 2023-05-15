package com.school.project.ecommercebackend.model.dao;

import com.school.project.ecommercebackend.model.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartItemDAO extends CrudRepository<Cart, Long> {
    Optional<Cart> findByLocalUser_Id(Long id);
}