package com.unq.purchase;

import com.unq.ParkingArea;

import java.math.BigInteger;
import java.time.LocalDateTime;

public abstract class Purchase {

    private BigInteger id;
    private ParkingArea area;
    private LocalDateTime date;

    public Purchase(BigInteger id, ParkingArea area, LocalDateTime date) {
        this.id = id;
        this.area = area;
        this.date = date;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public ParkingArea getArea() {
        return area;
    }

    public void setArea(ParkingArea area) {
        this.area = area;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
