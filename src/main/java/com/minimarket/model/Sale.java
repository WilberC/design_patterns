package com.minimarket.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SALE")
public class Sale extends Transaction {

    private String customer;

    public Sale() {
        super();
    }

    public Sale(Product product, Integer quantity, Double total, String customer) {
        super(product, quantity, total);
        this.customer = customer;
    }

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }
}
