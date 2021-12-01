package com.unq.app.sem.activities;

import com.unq.app.sem.Duration;
import com.unq.commons.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EndParkingResponseTest {

    private TimeUtil timeUtil;

    @BeforeEach
    public void setUp() {
        timeUtil = mock(TimeUtil.class);
    }

    @Test
    public void constructMessageTest() {
        when(timeUtil.nowTime()).thenReturn(LocalTime.of(12,10));

        EndParkingResponse startParkingResponse = EndParkingResponse.newBuilder()
                .startHour(timeUtil.nowTime())
                .endHour(timeUtil.nowTime().plusHours(1))
                .duration(new Duration(1L, 0L))
                .cost(40D)
                .build();

        String expectedMessage = "End parking with the following information: Start hour: 12:10, End hour: 13:10, Duration: 1:0, Cost:40.0";

        assertEquals(expectedMessage, startParkingResponse.message());
    }

}
