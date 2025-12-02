package com.minimarket.service.strategy;

public interface PricingStrategy {
    double calculateTotal(double price, int quantity);
}
