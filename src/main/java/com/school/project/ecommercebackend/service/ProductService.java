package com.school.project.ecommercebackend.service;

import com.school.project.ecommercebackend.model.Product;
import com.school.project.ecommercebackend.model.dao.ProductsDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductsDAO productsDAO;

    public ProductService(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    public List<Product> getProducts(){
        return productsDAO.findAll();
    }
}
