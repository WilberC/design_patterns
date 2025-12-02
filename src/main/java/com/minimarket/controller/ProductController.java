package com.minimarket.controller;

import com.minimarket.model.Product;
import com.minimarket.service.facade.MinimarketFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final MinimarketFacade minimarketFacade;

    @Autowired
    public ProductController(MinimarketFacade minimarketFacade) {
        this.minimarketFacade = minimarketFacade;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", minimarketFacade.getAllProducts());
        return "products";
    }

    @GetMapping("/new")
    public String showAddForm() {
        return "product_form";
    }

    @PostMapping("/save")
    public String saveProduct(@RequestParam String code,
                              @RequestParam String name,
                              @RequestParam Double price,
                              @RequestParam Integer stock) {

        // Builder Pattern Usage
        Product product = new Product.Builder(code, name)
                .price(price)
                .stock(stock)
                .build();

        minimarketFacade.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        minimarketFacade.deleteProduct(id);
        return "redirect:/products";
    }
}
