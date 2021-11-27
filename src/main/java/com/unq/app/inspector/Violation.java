package com.unq.app.inspector;

import com.unq.parking.ParkingArea;

import java.time.LocalDateTime;

public class Violation {

    private String patent;
    private LocalDateTime dateTime;
    private Inspector inspector;
    private ParkingArea parkingArea;

    public Violation(String patent, LocalDateTime dateTime, Inspector inspector, ParkingArea parkingArea) {
        this.patent = patent;
        this.dateTime = dateTime;
        this.inspector = inspector;
        this.parkingArea = parkingArea;
    }

    public String getPatent() {
        return patent;
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Inspector getInspector() {
        return inspector;
    }

    public ParkingArea getParkingArea() {
        return parkingArea;
    }
}
