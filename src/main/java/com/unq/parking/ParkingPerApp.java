package com.unq.parking;

import java.time.LocalTime;

public class ParkingPerApp extends Parking {

    private String phoneNumber;
    private ParkingSystem parkingSystem;

    public ParkingPerApp(String carPatent,LocalTime startTime, String phoneNumber) {
        super(carPatent, startTime);
        this.phoneNumber = phoneNumber;
        this.parkingSystem = ParkingSystem.getInstance();
    }

    @Override
    public LocalTime timeLimit() {
        return parkingSystem.getEndTime();
    }

    public void setParkingSystem(ParkingSystem parkingSystem) {
        this.parkingSystem = parkingSystem;
    }
}
