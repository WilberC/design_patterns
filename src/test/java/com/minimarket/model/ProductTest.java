package com.minimarket.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void builder_CreatesProductWithCorrectValues() {
        Product product = new Product.Builder("P001", "Test Product")
                .id(1L)
                .price(100.0)
                .stock(50)
                .build();

        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("P001", product.getCode());
        assertEquals("Test Product", product.getName());
        assertEquals(100.0, product.getPrice());
        assertEquals(50, product.getStock());
    }

    @Test
    void builder_MinimalArguments_CreatesProduct() {
        Product product = new Product.Builder("P002", "Minimal Product").build();

        assertNotNull(product);
        assertEquals("P002", product.getCode());
        assertEquals("Minimal Product", product.getName());
        assertNull(product.getId());
        assertNull(product.getPrice());
        assertNull(product.getStock());
    }
}
