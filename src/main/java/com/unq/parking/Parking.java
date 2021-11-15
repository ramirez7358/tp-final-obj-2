package com.unq.parking;

import java.time.LocalDateTime;
import java.time.LocalTime;
import com.unq.TimeUtil;


public abstract class Parking {
    private String carPatent;
    private LocalTime creationTime;
    private LocalTime endTime;
    private TimeUtil timeUtil;

    public Parking(String carPatent) {
        this.carPatent = carPatent;
        this.timeUtil = new TimeUtil();
        this.creationTime = timeUtil.nowTime();
    }

    public abstract Boolean inForce();

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

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public TimeUtil getTimeUtil() {
        return timeUtil;
    }

    public void setTimeUtil(TimeUtil timeUtil) {
        this.timeUtil = timeUtil;
    }
}
