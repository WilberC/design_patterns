package com.minimarket.service;

import com.minimarket.model.Transaction;
import com.minimarket.repository.TransactionRepository;
import com.minimarket.service.report.TransactionReportGenerator;
import com.minimarket.service.report.formatter.ReportFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final TransactionRepository transactionRepository;
    private final Map<String, ReportFormatter> formatters;

    @Autowired
    public ReportService(TransactionRepository transactionRepository,
                         Map<String, ReportFormatter> formatters) {
        this.transactionRepository = transactionRepository;
        this.formatters = formatters;
    }

    public String generateReport(String type) {
        List<Transaction> transactions = transactionRepository.findAll();

        // Bridge Pattern: Select formatter (Implementor)
        ReportFormatter formatter = formatters.get(type + "Formatter");
        if (formatter == null) {
            throw new IllegalArgumentException("Unknown report format: " + type);
        }

        // Bridge Pattern: Create generator (Abstraction) with selected formatter
        TransactionReportGenerator generator = new TransactionReportGenerator(formatter);

        return generator.generateReport(transactions);
    }
}
