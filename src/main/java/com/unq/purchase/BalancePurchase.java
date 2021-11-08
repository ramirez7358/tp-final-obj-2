package com.unq.purchase;

import com.unq.ParkingArea;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class BalancePurchase extends Purchase{

    private Float amount;
    private String phoneNumber;

    public BalancePurchase(BigInteger id,
                           ParkingArea area,
                           LocalDateTime date,
                           Float amount,
                           String phoneNumber) {
        super(id, area, date);
        this.amount = amount;
        this.phoneNumber = phoneNumber;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
