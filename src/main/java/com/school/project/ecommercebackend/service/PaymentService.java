package com.school.project.ecommercebackend.service;

import com.school.project.ecommercebackend.api.model.PaymentRequest;
import com.school.project.ecommercebackend.model.Payment;
import com.school.project.ecommercebackend.model.dao.PaymentDAO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    @Autowired
    private PaymentDAO paymentRepository;

    public Session checkout() throws StripeException {
        List<Object> lineItems = new ArrayList<>();
        Map<String, Object> lineItem1 = new HashMap<>();
        lineItem1.put("price", "price_H5ggYwtDq4fbrJ");
        lineItem1.put("quantity", 2);
        lineItems.add(lineItem1);
        Map<String, Object> params = new HashMap<>();
        params.put(
                "success_url",
                "https://example.com/success"
        );
        params.put("line_items", lineItems);
        params.put("mode", "payment");

        return Session.create(params);
    }

    public void charge(PaymentRequest paymentRequest) throws StripeException {
        Stripe.apiKey = secretKey;

        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setDescription(paymentRequest.getDescription());
        paymentRepository.save(payment);

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentRequest.getAmount());
        params.put("currency", paymentRequest.getCurrency());
        params.put("description", paymentRequest.getDescription());
        params.put("source", paymentRequest.getStripeToken());
        Charge charge = Charge.create(params);

    }
}