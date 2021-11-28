package com.unq.purchase;

import com.unq.parking.ParkingArea;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class HoursPurchase extends Purchase{

    private Integer quantity;
    private String patent;

    public HoursPurchase(ParkingArea area,
                         LocalDateTime date,
                         Integer quantity,
                         String patent) {
        super(area, date);
        this.quantity = quantity;
        this.patent = patent;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getPatent() {
        return patent;
    }
}
