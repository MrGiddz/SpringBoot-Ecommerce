package com.school.project.ecommercebackend.api.model;

import com.school.project.ecommercebackend.model.LocalUser;
import com.school.project.ecommercebackend.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CartRequest {
    @NotNull
    @NotBlank
    private Long productId;
    @NotNull
    @NotBlank
    private Double price;
    @NotNull
    @NotBlank
    private Integer quantity;


    private LocalUser user;

    public LocalUser getUser() {
        return user;
    }

    public void setUser(LocalUser user) {
        this.user = user;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
