package com.unq.parking;

import java.time.LocalTime;

public class ParkingPerApp extends Parking {

    public ParkingPerApp(String carPatent) {
        super(carPatent);
    }

    @Override
    public Boolean inForce() {
        return null;
    }
}
