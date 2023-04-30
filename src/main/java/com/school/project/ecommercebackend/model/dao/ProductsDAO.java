package com.school.project.ecommercebackend.model.dao;

import com.school.project.ecommercebackend.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductsDAO extends ListCrudRepository<Product, Long> {
}
