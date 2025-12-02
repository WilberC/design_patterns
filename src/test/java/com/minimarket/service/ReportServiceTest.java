package com.minimarket.service;

import com.minimarket.model.Product;
import com.minimarket.model.Sale;
import com.minimarket.model.Transaction;
import com.minimarket.repository.TransactionRepository;
import com.minimarket.service.report.formatter.ReportFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ReportFormatter reportFormatter;

    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Map<String, ReportFormatter> formatters = new HashMap<>();
        formatters.put("csvFormatter", reportFormatter);

        reportService = new ReportService(transactionRepository, formatters);
    }

    @Test
    void generateReport_ValidType_GeneratesReport() {
        Product product = new Product();
        product.setName("Test Product");

        Transaction transaction = new Sale();
        transaction.setId(1L);
        transaction.setDate(LocalDateTime.now());
        transaction.setProduct(product);
        transaction.setQuantity(1);
        transaction.setTotal(10.0);

        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(transaction));
        when(reportFormatter.formatHeader(anyList())).thenReturn("Header\n");
        when(reportFormatter.formatRow(anyList())).thenReturn("Row\n");
        when(reportFormatter.formatFooter(anyString())).thenReturn("Footer");

        String report = reportService.generateReport("csv");

        assertNotNull(report);
        assertTrue(report.contains("Header"));
        assertTrue(report.contains("Row"));
        assertTrue(report.contains("Footer"));

        verify(transactionRepository).findAll();
        verify(reportFormatter).formatHeader(anyList());
        verify(reportFormatter).formatRow(anyList());
        verify(reportFormatter).formatFooter(anyString());
    }

    @Test
    void generateReport_UnknownType_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            reportService.generateReport("unknown")
        );
    }
}
