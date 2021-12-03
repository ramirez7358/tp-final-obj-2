package com.unq.purchase;

import com.unq.parking.ParkingArea;

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

    public ParkingArea getArea() {
        return area;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
