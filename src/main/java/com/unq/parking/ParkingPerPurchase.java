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
    public Boolean inForce() {
        LocalTime now = this.getTimeUtil().nowTime();
        return now.isAfter(this.getCreationTime()) && now.isBefore(this.getEndTime());
    }

    public Purchase getPurchaseRegistry() {
        return purchaseRegistry;
    }

    public void setPurchaseRegistry(Purchase purchaseRegistry) {
        this.purchaseRegistry = purchaseRegistry;
    }
}
