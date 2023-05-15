package com.school.project.ecommercebackend.service;

import com.school.project.ecommercebackend.model.LocalUser;
import com.school.project.ecommercebackend.model.WebOrder;
import com.school.project.ecommercebackend.model.dao.WebOrderDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private WebOrderDAO webOrderDAO;

    public OrderService (WebOrderDAO webOrderDAO) {
        this.webOrderDAO = webOrderDAO;
    }

    public List<WebOrder> getOrders(LocalUser user){
        return webOrderDAO.findByUser(user);
    }
}
