package com.unq;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {

    public LocalTime nowTime() {
        return LocalTime.now();
    }

    public LocalTime timeOf(int hour, int minutes, int seconds){
        return LocalTime.of(hour,minutes,seconds);
    }

    public LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }

}
