package com.unq.app.sem;

import com.unq.app.sem.activities.EndParkingResponse;
import com.unq.app.sem.activities.StartParkingResponse;
import com.unq.app.sem.movement.DrivingState;
import com.unq.parking.ParkingArea;
import com.unq.commons.TimeUtil;
import com.unq.parking.Parking;
import com.unq.parking.ParkingPerApp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppSEMTest {

    private static ParkingArea area;
    private static TimeUtil timeUtil;

    @BeforeAll
    public static void setUp() {
        area = mock(ParkingArea.class);
        timeUtil = mock(TimeUtil.class);
    }

    @Test
    public void calculateMaxHours() {
        AppSEM appSEM = new AppSEM("1234567891", "MBS865", new DrivingState());

        double maxHours = appSEM.getMaxHours();

        Assertions.assertEquals(4, maxHours);
    }

    @Test
    public void calculateMaxWithZeroBalance() {
        AppSEM appSEM = new AppSEM("1234567891", "MBS865", new DrivingState());

        double maxHours = appSEM.getMaxHours();

        Assertions.assertEquals(0, maxHours);
    }

    @Test
    public void calculateHalfHour() {
        AppSEM appSEM = new AppSEM("1234567891", "MBS865", new DrivingState());

        double maxHours = appSEM.getMaxHours();

        Assertions.assertEquals(0.5, maxHours);
    }

    @Test
    public void startParkingInsufficientBalance(){
        AppSEM appSEM = new AppSEM("1234567891", "MBS865", new DrivingState());

        /*InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                appSEM.startParking("MBS912", "1130281723"),
                "Expected doThing() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Insufficient balance. Parking not allowed."));*/
    }

    @Test()
    public void startParking()  {
        AppSEM appSEM = new AppSEM("1234567891", "MBS865", new DrivingState());

        LocalTime timeMock = LocalTime.of(12, 0, 0);
        Parking parkingMock = new ParkingPerApp("MBC645", "1234567897");

        //when(area.createParking(Mockito.any(), Mockito.any())).getMock();
        when(timeUtil.nowTime()).thenReturn(timeMock);

        appSEM.startParking();
    }

    @Test
    public void endParking() {
        AppSEM appSEM = new AppSEM("1234567891", "MBS865", new DrivingState());

        LocalTime dateMock = LocalTime.of(18, 0, 0);
        LocalTime startParkingDateTime = LocalTime.of(16, 0, 0);

        //when(area.removeParking(Mockito.any())).thenReturn(parkingMock);
        when(timeUtil.nowTime()).thenReturn(dateMock);

        appSEM.endParking();
    }

}
