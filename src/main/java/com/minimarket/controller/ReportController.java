package com.minimarket.controller;

import com.minimarket.service.facade.MinimarketFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final MinimarketFacade minimarketFacade;

    @Autowired
    public ReportController(MinimarketFacade minimarketFacade) {
        this.minimarketFacade = minimarketFacade;
    }

    @GetMapping
    public String showReportPage() {
        return "reports";
    }

    @GetMapping("/generate")
    @ResponseBody
    public String generateReport(@RequestParam String type) {
        return minimarketFacade.generateReport(type);
    }
}
