package com.unq.parking;

import com.unq.purchase.Purchase;

import java.time.LocalTime;

public class ParkingPerPurchase extends Parking{
    private Purchase purchaseRegistry;

    public ParkingPerPurchase(String carPatent, LocalTime endTime, Purchase purchaseRegistry) {
        super(carPatent);
        this.setEndTime(endTime);
        this.purchaseRegistry = purchaseRegistry;
    }

    @Override
    public LocalTime timeLimit() {
        return this.getEndTime();
    }

    public Purchase getPurchaseRegistry() {
        return purchaseRegistry;
    }

    public void setPurchaseRegistry(Purchase purchaseRegistry) {
        this.purchaseRegistry = purchaseRegistry;
    }
}
