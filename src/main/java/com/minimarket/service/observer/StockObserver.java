package com.minimarket.service.observer;

import com.minimarket.model.Transaction;

public interface StockObserver {
    void onTransaction(Transaction transaction);
}
