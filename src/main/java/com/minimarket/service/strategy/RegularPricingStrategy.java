package com.minimarket.service.strategy;

import org.springframework.stereotype.Component;

@Component("regularPricing")
public class RegularPricingStrategy implements PricingStrategy {
    @Override
    public double calculateTotal(double price, int quantity) {
        return price * quantity;
    }
}
