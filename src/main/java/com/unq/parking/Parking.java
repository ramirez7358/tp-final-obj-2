package com.unq.parking;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Parking {
    private String carPatent;
    private LocalTime creationTime;

    public Parking(String carPatent,LocalTime creationTime) {
        this.carPatent = carPatent;
        this.creationTime = creationTime;
    }

    public Boolean inForce() {
        return true;
    }

    public String getCarPatent() {
        return carPatent;
    }

    public void setCarPatent(String carPatent) {
        this.carPatent = carPatent;
    }


    public LocalTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalTime creationTime) {
        this.creationTime = creationTime;
    }
}
