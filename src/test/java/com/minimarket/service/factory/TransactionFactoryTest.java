package com.minimarket.service.factory;

import com.minimarket.model.Product;
import com.minimarket.model.Purchase;
import com.minimarket.model.Sale;
import com.minimarket.model.Transaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TransactionFactoryTest {

    private final TransactionFactory factory = new TransactionFactory();

    @Test
    void createTransaction_PurchaseType_ReturnsPurchaseInstance() {
        Product product = new Product();
        Transaction transaction = factory.createTransaction("PURCHASE", product, 10, 100.0, "Supplier A");

        assertNotNull(transaction);
        assertTrue(transaction instanceof Purchase);
        assertEquals(product, transaction.getProduct());
        assertEquals(10, transaction.getQuantity());
        assertEquals(100.0, transaction.getTotal());
        assertEquals("Supplier A", ((Purchase) transaction).getSupplier());
    }

    @Test
    void createTransaction_SaleType_ReturnsSaleInstance() {
        Product product = new Product();
        Transaction transaction = factory.createTransaction("SALE", product, 5, 50.0, "Customer B");

        assertNotNull(transaction);
        assertTrue(transaction instanceof Sale);
        assertEquals(product, transaction.getProduct());
        assertEquals(5, transaction.getQuantity());
        assertEquals(50.0, transaction.getTotal());
        assertEquals("Customer B", ((Sale) transaction).getCustomer());
    }

    @Test
    void createTransaction_UnknownType_ThrowsException() {
        Product product = new Product();
        assertThrows(IllegalArgumentException.class, () ->
            factory.createTransaction("UNKNOWN", product, 1, 10.0, "Info")
        );
    }
}
