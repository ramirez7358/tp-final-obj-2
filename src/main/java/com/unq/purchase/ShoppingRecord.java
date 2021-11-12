package com.unq.purchase;

import java.util.ArrayList;
import java.util.List;

public class ShoppingRecord {

    private List<Purchase> purchases;

    private static ShoppingRecord instance;

    public static ShoppingRecord getInstance() {
        if(instance == null) {
            instance = new ShoppingRecord();
        }
        return instance;
    }

    private ShoppingRecord() {
        this.purchases = new ArrayList<>();
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
