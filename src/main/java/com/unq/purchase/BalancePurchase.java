package com.unq.purchase;

import com.unq.parking.ParkingArea;

import java.time.LocalDateTime;

public class BalancePurchase extends Purchase{

    private Double amount;
    private String phoneNumber;

    public BalancePurchase(ParkingArea area,
                           LocalDateTime date,
                           Double amount,
                           String phoneNumber) {
        super(area, date);
        this.amount = amount;
        this.phoneNumber = phoneNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
