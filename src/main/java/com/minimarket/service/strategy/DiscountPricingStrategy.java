package com.minimarket.service.strategy;

import org.springframework.stereotype.Component;

@Component("discountPricing")
public class DiscountPricingStrategy implements PricingStrategy {
    @Override
    public double calculateTotal(double price, int quantity) {
        // 10% discount for bulk orders (e.g., > 10 items)
        if (quantity > 10) {
            return (price * quantity) * 0.90;
        }
        return price * quantity;
    }
}
