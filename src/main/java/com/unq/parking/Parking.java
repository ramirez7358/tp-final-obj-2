package com.unq.parking;

import java.time.LocalDateTime;

public class Parking {
    private String carPatent;
    private ParkingStrategy parkingStrategy;
    private LocalDateTime creationDateTime;

    public Parking(String carPatent, ParkingStrategy parkingStrategy, LocalDateTime creationDateTime) {
        this.carPatent = carPatent;
        this.parkingStrategy = parkingStrategy;
        this.creationDateTime = creationDateTime;
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

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
