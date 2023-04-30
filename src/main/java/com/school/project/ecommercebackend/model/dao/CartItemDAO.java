package com.school.project.ecommercebackend.model.dao;

import com.school.project.ecommercebackend.model.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemDAO extends CrudRepository<CartItem, Long> {
}