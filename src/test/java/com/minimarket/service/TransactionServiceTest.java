package com.minimarket.service;

import com.minimarket.model.Product;
import com.minimarket.model.Sale;
import com.minimarket.model.Transaction;
import com.minimarket.repository.ProductRepository;
import com.minimarket.repository.TransactionRepository;
import com.minimarket.service.factory.TransactionFactory;
import com.minimarket.service.observer.StockObserver;
import com.minimarket.service.strategy.PricingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ProductRepository productRepository;

    private TransactionFactory transactionFactory = new TransactionFactory();

    @Mock
    private PricingStrategy pricingStrategy;

    @Mock
    private StockObserver stockObserver;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Map<String, PricingStrategy> strategies = new HashMap<>();
        strategies.put("regularPricing", pricingStrategy);

        List<StockObserver> observers = Collections.singletonList(stockObserver);

        transactionService = new TransactionService(
                transactionRepository,
                productRepository,
                transactionFactory,
                strategies,
                observers
        );
    }

    @Test
    void createTransaction_ValidInput_CreatesAndSavesTransaction() {
        Product product = new Product();
        product.setId(1L);
        product.setPrice(10.0);

        // We don't mock the factory anymore, so we don't need to setup the transaction return
        // The factory will create a Sale object

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(pricingStrategy.calculateTotal(10.0, 5)).thenReturn(50.0);

        // We need to mock the save to return the passed transaction (or a similar one)
        // Since we don't know the exact object instance the factory creates, we can use any() and return the argument
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        Transaction result = transactionService.createTransaction("SALE", 1L, 5, "regularPricing", "None");

        assertNotNull(result);
        assertTrue(result instanceof Sale);
        assertEquals(50.0, result.getTotal());

        verify(productRepository).findById(1L);
        verify(pricingStrategy).calculateTotal(10.0, 5);
        verify(transactionRepository).save(any(Transaction.class));
        verify(stockObserver).onTransaction(any(Transaction.class));
    }

    @Test
    void createTransaction_ProductNotFound_ThrowsException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
            transactionService.createTransaction("SALE", 1L, 5, "regularPricing", "None")
        );
    }
}
