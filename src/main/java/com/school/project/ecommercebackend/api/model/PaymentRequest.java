package com.school.project.ecommercebackend.api.model;

public class PaymentRequest {
    private String description;
    private double amount;
    private String currency;
    private String stripeToken;


    public PaymentRequest() {}

    public PaymentRequest(String description, double amount, String currency, String stripeToken) {
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.stripeToken = stripeToken;
    }

    // getters and setters

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

}
