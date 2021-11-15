package com.unq.parking;

import java.time.LocalTime;

public class ParkingPerPurchaseStrategy extends Parking{
    public ParkingPerPurchaseStrategy(String carPatent, LocalTime creationTime) {
        super(carPatent,  creationTime);
    }

    @Override
    public Boolean inForce() {
        return null;
    }
}
