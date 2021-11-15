package com.unq.parking;

import java.time.LocalTime;

public class ParkingPerApp extends Parking {

    public ParkingPerApp(String carPatent, LocalTime creationTime) {
        super(carPatent, creationTime);
    }

    @Override
    public Boolean inForce() {
        return null;
    }
}
