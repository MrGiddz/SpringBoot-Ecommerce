package com.school.project.ecommercebackend.api.controller.payments;

import com.school.project.ecommercebackend.api.model.CheckoutRequest;
import com.school.project.ecommercebackend.api.model.PaymentRequest;
import com.school.project.ecommercebackend.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;



    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody CheckoutRequest checkoutRequest, HttpServletResponse response) {
        try {
            Session session = paymentService.checkout(checkoutRequest);
            String checkoutUrl = session.getUrl();
            response.sendRedirect(checkoutUrl);
        } catch (StripeException | IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @PostMapping("/charge")
    public ResponseEntity<String> charge(@RequestBody PaymentRequest paymentRequest) {
        try {
            paymentService.charge(paymentRequest);
            return ResponseEntity.ok("Payment successful");
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/success")
    public String handleSuccess() {
        // Handle successful checkout
        return "success";
    }

    @GetMapping("/cancel")
    public String handleCancel() {
        // Handle cancelled checkout
        return "cancel";
    }
}