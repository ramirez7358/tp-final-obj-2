package com.unq.app.sem.activities;

import com.unq.commons.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StartParkingResponseTest {

    private TimeUtil timeUtil;

    @BeforeEach
    public void setUp() {
        timeUtil = mock(TimeUtil.class);
    }

    @Test
    public void constructMessageTest() {
        when(timeUtil.nowTime()).thenReturn(LocalTime.of(12,12));

        StartParkingResponse startParkingResponse = StartParkingResponse.newBuilder()
                .startHour(timeUtil.nowTime())
                .maxHour(timeUtil.nowTime().plusHours(2))
                .build();

        String expectedMessage = "Start parking with the following information: Start hour: 12:12, Max hours: 14:12";

        assertEquals(expectedMessage, startParkingResponse.message());
    }

}
