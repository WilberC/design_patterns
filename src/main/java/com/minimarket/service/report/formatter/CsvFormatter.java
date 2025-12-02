package com.minimarket.service.report.formatter;

import org.springframework.stereotype.Component;
import java.util.List;

@Component("csvFormatter")
public class CsvFormatter implements ReportFormatter {

    @Override
    public String formatHeader(List<String> headers) {
        return String.join(",", headers) + "\n";
    }

    @Override
    public String formatRow(List<String> values) {
        return String.join(",", values) + "\n";
    }

    @Override
    public String formatFooter(String footer) {
        return "-- " + footer + " --";
    }
}
