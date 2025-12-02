package com.minimarket.controller;

import com.minimarket.service.facade.MinimarketFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final MinimarketFacade minimarketFacade;

    @Autowired
    public TransactionController(MinimarketFacade minimarketFacade) {
        this.minimarketFacade = minimarketFacade;
    }

    @GetMapping
    public String listTransactions(Model model) {
        model.addAttribute("transactions", minimarketFacade.getAllTransactions());
        return "transactions";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("products", minimarketFacade.getAllProducts());
        return "transaction_form";
    }

    @PostMapping("/save")
    public String saveTransaction(@RequestParam String type,
                                  @RequestParam Long productId,
                                  @RequestParam Integer quantity,
                                  @RequestParam(defaultValue = "regularPricing") String strategy,
                                  @RequestParam(required = false) String extraInfo) {

        minimarketFacade.createTransaction(type, productId, quantity, strategy, extraInfo);
        return "redirect:/transactions";
    }
}
