package com.unq.parking;

import java.time.LocalTime;

public class ParkingPerApp extends Parking {

    private String phoneNumber;
    public ParkingPerApp(String carPatent, String phoneNumber) {
        super(carPatent);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Boolean inForce() {
        LocalTime now = this.getTimeUtil().nowTime();
        return now.isAfter(this.getCreationTime()) && now.isBefore(ParkingSystem.END_TIME);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
