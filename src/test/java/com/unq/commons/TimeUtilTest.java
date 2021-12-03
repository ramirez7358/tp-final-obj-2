package com.unq.commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeUtilTest {

    private TimeUtil timeUtil;

    @BeforeEach
    public void setUp() {
        timeUtil = new TimeUtil();
    }

    @Test
    public void calculateTimeFromTest(){
        Long difference = timeUtil.calculateTimeFrom(timeUtil.nowTime().minusHours(2L));

        assertEquals(120, difference);
    }

}
