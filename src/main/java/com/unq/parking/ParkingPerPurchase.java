package com.unq.parking;

import java.time.LocalTime;

public class ParkingPerPurchase extends Parking{
    public ParkingPerPurchase(String carPatent, LocalTime creationTime) {
        super(carPatent,  creationTime);
    }

    @Override
    public Boolean inForce() {
        return null;
    }
}
