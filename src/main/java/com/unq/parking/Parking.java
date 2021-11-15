package com.unq.parking;

import java.time.LocalDateTime;
import java.time.LocalTime;
import com.unq.TimeUtil;


public class Parking {
    private String carPatent;
    private LocalTime creationTime;
    private TimeUtil timeUtil;

    public Parking(String carPatent) {
        this.carPatent = carPatent;
        this.creationTime = timeUtil.nowTime();
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
