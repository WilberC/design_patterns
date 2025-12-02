package com.minimarket.service.factory;

import com.minimarket.model.Product;
import com.minimarket.model.Purchase;
import com.minimarket.model.Sale;
import com.minimarket.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionFactory {

    public Transaction createTransaction(String type, Product product, int quantity, double total, String extraInfo) {
        if ("PURCHASE".equalsIgnoreCase(type)) {
            return new Purchase(product, quantity, total, extraInfo);
        } else if ("SALE".equalsIgnoreCase(type)) {
            return new Sale(product, quantity, total, extraInfo);
        }
        throw new IllegalArgumentException("Unknown transaction type: " + type);
    }
}
