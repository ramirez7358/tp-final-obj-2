package com.unq.purchase;

import com.unq.ParkingArea;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class HoursPurchase extends Purchase{

    private Integer quantity;
    private String patent;

    public HoursPurchase(BigInteger id,
                         ParkingArea area,
                         LocalDateTime date,
                         Integer quantity,
                         String patent) {
        super(id, area, date);
        this.quantity = quantity;
        this.patent = patent;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }
}
