package com.school.project.ecommercebackend.api.controller.payments;

import com.school.project.ecommercebackend.api.model.PaymentRequest;
import com.school.project.ecommercebackend.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/charge")
    public ResponseEntity<String> charge(@RequestBody PaymentRequest paymentRequest) {
        try {
            paymentService.charge(paymentRequest);
            return ResponseEntity.ok("Payment successful");
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}