package com.unq.purchase;

import com.unq.commons.TimeUtil;
import com.unq.parking.ParkingArea;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Purchase {

    private static final AtomicInteger atomicId = new AtomicInteger(1000);
    private int id;
    private ParkingArea area;
    private LocalDateTime date;

    public Purchase(ParkingArea area, LocalDateTime localDateTime) {
        this.id = atomicId.incrementAndGet();
        this.area = area;
        this.date = localDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
