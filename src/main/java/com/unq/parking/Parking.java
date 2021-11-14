package com.unq.parking;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Parking {
    private String carPatent;
    private ParkingStrategy parkingStrategy;
    private LocalTime creationTime;

    public Parking(String carPatent, ParkingStrategy parkingStrategy, LocalTime creationTime) {
        this.carPatent = carPatent;
        this.parkingStrategy = parkingStrategy;
        this.creationTime = creationTime;
    }

    public String getCarPatent() {
        return carPatent;
    }

    public void setCarPatent(String carPatent) {
        this.carPatent = carPatent;
    }

    public ParkingStrategy getParkingStrategy() {
        return parkingStrategy;
    }

    public void setParkingStrategy(ParkingStrategy parkingStrategy) {
        this.parkingStrategy = parkingStrategy;
    }

    public LocalTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalTime creationTime) {
        this.creationTime = creationTime;
    }
}
