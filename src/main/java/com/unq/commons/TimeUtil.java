package com.unq.commons;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

    public Long calculateTimeFrom(LocalTime localTime) {
        return ChronoUnit.MINUTES.between(localTime, this.nowTime());
    }

    public LocalTime nowTime() {
        return LocalTime.now();
    }

    public LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }

}
