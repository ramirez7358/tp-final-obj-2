package com.unq.parking;

import java.time.LocalTime;

public class ParkingPerPurchase extends Parking{
    public ParkingPerPurchase(String carPatent) {
        super(carPatent);
    }

    @Override
    public Boolean inForce() {
        return null;
    }
}
