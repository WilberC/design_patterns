package com.minimarket.service.report;

import com.minimarket.model.Transaction;
import com.minimarket.service.report.formatter.ReportFormatter;
import java.util.List;

public abstract class ReportGenerator {

    protected final ReportFormatter formatter;

    protected ReportGenerator(ReportFormatter formatter) {
        this.formatter = formatter;
    }

    // Template Method
    public final String generateReport(List<Transaction> transactions) {
        StringBuilder report = new StringBuilder();
        report.append(formatter.formatHeader(getHeaderLabels()));

        for (Transaction t : transactions) {
            report.append(formatter.formatRow(getRowData(t)));
        }

        report.append(formatter.formatFooter(getFooterText()));
        return report.toString();
    }

    protected abstract List<String> getHeaderLabels();
    protected abstract List<String> getRowData(Transaction t);
    protected abstract String getFooterText();
}
