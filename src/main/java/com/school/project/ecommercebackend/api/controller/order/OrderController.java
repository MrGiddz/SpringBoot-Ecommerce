package com.school.project.ecommercebackend.api.controller.order;

import com.school.project.ecommercebackend.model.LocalUser;
import com.school.project.ecommercebackend.model.WebOrder;
import com.school.project.ecommercebackend.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping
    public List<WebOrder> getOrders(@AuthenticationPrincipal LocalUser user){
        return orderService.getOrders(user);
    }

}
