package com.school.project.ecommercebackend.api.controller.products;

import com.school.project.ecommercebackend.model.LocalUser;
import com.school.project.ecommercebackend.model.Product;
import com.school.project.ecommercebackend.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }
}
