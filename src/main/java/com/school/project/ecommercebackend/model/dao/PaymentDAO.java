package com.school.project.ecommercebackend.model.dao;

import com.school.project.ecommercebackend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDAO extends JpaRepository<Payment, Long> {
}