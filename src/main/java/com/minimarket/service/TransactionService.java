package com.minimarket.service;

import com.minimarket.model.Product;
import com.minimarket.model.Transaction;
import com.minimarket.repository.ProductRepository;
import com.minimarket.repository.TransactionRepository;
import com.minimarket.service.factory.TransactionFactory;
import com.minimarket.service.observer.StockObserver;
import com.minimarket.service.strategy.PricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final TransactionFactory transactionFactory;
    private final Map<String, PricingStrategy> pricingStrategies;
    private final List<StockObserver> stockObservers;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              ProductRepository productRepository,
                              TransactionFactory transactionFactory,
                              Map<String, PricingStrategy> pricingStrategies,
                              List<StockObserver> stockObservers) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.transactionFactory = transactionFactory;
        this.pricingStrategies = pricingStrategies;
        this.stockObservers = stockObservers;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction createTransaction(String type, Long productId, int quantity, String strategyName, String extraInfo) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Strategy Pattern: Select pricing strategy
        PricingStrategy strategy = pricingStrategies.getOrDefault(strategyName, pricingStrategies.get("regularPricing"));
        double total = strategy.calculateTotal(product.getPrice(), quantity);

        // Factory Pattern: Create transaction
        Transaction transaction = transactionFactory.createTransaction(type, product, quantity, total, extraInfo);

        // Save transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Observer Pattern: Notify observers (e.g., update stock)
        notifyObservers(savedTransaction);

        return savedTransaction;
    }

    private void notifyObservers(Transaction transaction) {
        for (StockObserver observer : stockObservers) {
            observer.onTransaction(transaction);
        }
    }
}
