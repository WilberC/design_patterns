package com.minimarket.service.observer;

import com.minimarket.model.Product;
import com.minimarket.model.Purchase;
import com.minimarket.model.Sale;
import com.minimarket.model.Transaction;
import com.minimarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockUpdater implements StockObserver {

    private final ProductRepository productRepository;

    @Autowired
    public StockUpdater(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void onTransaction(Transaction transaction) {
        Product product = transaction.getProduct();
        int quantity = transaction.getQuantity();

        if (transaction instanceof Purchase) {
            product.setStock(product.getStock() + quantity);
        } else if (transaction instanceof Sale) {
            product.setStock(product.getStock() - quantity);
        }

        productRepository.save(product);
    }
}
