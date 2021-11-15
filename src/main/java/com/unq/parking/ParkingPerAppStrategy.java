package com.unq.parking;

import java.time.LocalTime;

public class ParkingPerAppStrategy extends Parking {

    public ParkingPerAppStrategy(String carPatent, LocalTime creationTime) {
        super(carPatent, creationTime);
    }

    @Override
    public Boolean inForce() {
        return null;
    }
}
