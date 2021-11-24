package com.unq.parking;

import java.time.LocalTime;

public class ParkingPerApp extends Parking {

    private String phoneNumber;
    private ParkingSystem parkingSystem;

    public ParkingPerApp(String carPatent, String phoneNumber) {
        super(carPatent);
        this.phoneNumber = phoneNumber;
        this.parkingSystem = ParkingSystem.getInstance();
    }

    @Override
    public Boolean inForce() {
        LocalTime now = this.getTimeUtil().nowTime();
        return now.isAfter(this.getCreationTime()) && now.isBefore(parkingSystem.getEndTime());
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ParkingSystem getParkingSystem() {
        return parkingSystem;
    }

    public void setParkingSystem(ParkingSystem parkingSystem) {
        this.parkingSystem = parkingSystem;
    }
}
