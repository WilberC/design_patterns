package com.minimarket.service.report.formatter;

import org.springframework.stereotype.Component;
import java.util.List;

@Component("htmlFormatter")
public class HtmlFormatter implements ReportFormatter {

    @Override
    public String formatHeader(List<String> headers) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><table border='1'><tr>");
        for (String h : headers) {
            sb.append("<th>").append(h).append("</th>");
        }
        sb.append("</tr>");
        return sb.toString();
    }

    @Override
    public String formatRow(List<String> values) {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        for (String v : values) {
            sb.append("<td>").append(v).append("</td>");
        }
        sb.append("</tr>");
        return sb.toString();
    }

    @Override
    public String formatFooter(String footer) {
        return "</table><p>" + footer + "</p></body></html>";
    }
}
