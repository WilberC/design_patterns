package com.minimarket.service;

import com.minimarket.model.Product;
import com.minimarket.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts_ReturnsListOfProducts() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Apple");
        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Banana");

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_ProductExists_ReturnsProduct() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Apple");

        when(productRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Product> found = productService.getProductById(1L);

        assertTrue(found.isPresent());
        assertEquals("Apple", found.get().getName());
    }

    @Test
    void saveProduct_ReturnsSavedProduct() {
        Product p = new Product();
        p.setName("Apple");

        when(productRepository.save(p)).thenReturn(p);

        Product saved = productService.saveProduct(p);

        assertNotNull(saved);
        assertEquals("Apple", saved.getName());
        verify(productRepository, times(1)).save(p);
    }

    @Test
    void deleteProduct_CallsRepositoryDelete() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}
