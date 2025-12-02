package com.minimarket.service.report;

import com.minimarket.model.Transaction;
import com.minimarket.service.report.formatter.ReportFormatter;
import java.util.Arrays;
import java.util.List;

public class TransactionReportGenerator extends ReportGenerator {

    public TransactionReportGenerator(ReportFormatter formatter) {
        super(formatter);
    }

    @Override
    protected List<String> getHeaderLabels() {
        return Arrays.asList("ID", "Date", "Type", "Product", "Quantity", "Total");
    }

    @Override
    protected List<String> getRowData(Transaction t) {
        return Arrays.asList(
                String.valueOf(t.getId()),
                t.getDate().toString(),
                t.getClass().getSimpleName(),
                t.getProduct().getName(),
                String.valueOf(t.getQuantity()),
                String.valueOf(t.getTotal())
        );
    }

    @Override
    protected String getFooterText() {
        return "End of Transaction Report";
    }
}
