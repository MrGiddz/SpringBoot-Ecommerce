package com.school.project.ecommercebackend.service;

import com.school.project.ecommercebackend.api.model.CheckoutRequest;
import com.school.project.ecommercebackend.api.model.PaymentRequest;
import com.school.project.ecommercebackend.model.dao.PaymentDAO;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    @Autowired
    private PaymentDAO paymentRepository;

    public Session checkout(CheckoutRequest checkoutRequest) throws StripeException {
//        List<Object> lineItems = new ArrayList<>();
//        Map<String, Object> lineItem1 = new HashMap<>();
//        lineItem1.put("price", "price_H5ggYwtDq4fbrJ");
//        lineItem1.put("quantity", 2);
//        lineItems.add(lineItem1);
//        Map<String, Object> param = new HashMap<>();
//        param.put(
//                "success_url",
//                "https://example.com/success"
//        );
//        param.put("line_items", lineItems);
//        param.put("mode", "payment");


        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(checkoutRequest.getQuantity())
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(checkoutRequest.getPrice())
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(checkoutRequest.getProductName())
                                                                .build())
                                                .build())
                                .build())
                .build();
        return Session.create(params);
    }

    public Session charge(PaymentRequest paymentRequest) throws StripeException {
        String YOUR_DOMAIN = "http://localhost:4242";
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(YOUR_DOMAIN + "?success=true")
                        .setCancelUrl(YOUR_DOMAIN + "?canceled=true")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
                                        .setPrice("{{PRICE_ID}}")
                                        .build())
                        .build();

        return Session.create(params);
    }
}
