package com.unq.app.sem;

public class Duration {

    private Long hours;
    private Long minutes;

    public Duration(Long hours, Long minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }
}
