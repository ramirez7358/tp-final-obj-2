package com.unq.parking;

import java.time.LocalTime;
import com.unq.commons.TimeUtil;


public abstract class Parking {
    private String carPatent;
    private LocalTime creationTime;
    private LocalTime endTime;
    private TimeUtil timeUtil;

    public Parking(String carPatent, LocalTime startTime) {
        this.carPatent = carPatent;
        this.timeUtil = new TimeUtil();
        this.creationTime = startTime;
    }

    public Boolean inForce(){
        LocalTime now = timeUtil.nowTime();
        return now.isAfter(this.getCreationTime()) && now.isBefore(timeLimit());
    }

    public abstract LocalTime timeLimit();

    public String getCarPatent() {
        return carPatent;
    }

    public LocalTime getCreationTime() {
        return creationTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setTimeUtil(TimeUtil timeUtil) {
        this.timeUtil = timeUtil;
    }
}
