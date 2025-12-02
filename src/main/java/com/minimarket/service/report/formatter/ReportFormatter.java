package com.minimarket.service.report.formatter;

import java.util.List;

public interface ReportFormatter {
    String formatHeader(List<String> headers);
    String formatRow(List<String> values);
    String formatFooter(String footer);
}
