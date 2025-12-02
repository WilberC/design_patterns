package com.minimarket.service.facade;

import com.minimarket.model.Product;
import com.minimarket.model.Transaction;
import com.minimarket.service.ProductService;
import com.minimarket.service.ReportService;
import com.minimarket.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MinimarketFacade {

    private final ProductService productService;
    private final TransactionService transactionService;
    private final ReportService reportService;

    @Autowired
    public MinimarketFacade(ProductService productService,
                            TransactionService transactionService,
                            ReportService reportService) {
        this.productService = productService;
        this.transactionService = transactionService;
        this.reportService = reportService;
    }

    // Product Operations
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public Optional<Product> getProductById(Long id) {
        return productService.getProductById(id);
    }

    public Product saveProduct(Product product) {
        return productService.saveProduct(product);
    }

    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }

    // Transaction Operations
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    public Transaction createTransaction(String type, Long productId, int quantity, String strategyName, String extraInfo) {
        return transactionService.createTransaction(type, productId, quantity, strategyName, extraInfo);
    }

    // Report Operations
    public String generateReport(String type) {
        return reportService.generateReport(type);
    }
}
