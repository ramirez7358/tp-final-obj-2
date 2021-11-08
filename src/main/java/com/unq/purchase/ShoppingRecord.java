package com.unq.purchase;

import java.util.List;

public class ShoppingRecord {

    private List<Purchase> purchases;

    public ShoppingRecord(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void addPurchase(Purchase purchase) {
        this.purchases.add(purchase);
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
