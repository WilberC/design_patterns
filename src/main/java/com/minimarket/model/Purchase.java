package com.minimarket.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PURCHASE")
public class Purchase extends Transaction {

    private String supplier;

    public Purchase() {
        super();
    }

    public Purchase(Product product, Integer quantity, Double total, String supplier) {
        super(product, quantity, total);
        this.supplier = supplier;
    }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
}
