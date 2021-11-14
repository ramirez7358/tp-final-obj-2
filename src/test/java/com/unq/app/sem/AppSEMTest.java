package com.unq.app.sem;

import com.unq.parking.ParkingArea;
import com.unq.TimeUtil;
import com.unq.exceptions.InsufficientBalanceException;
import com.unq.parking.Parking;
import com.unq.parking.ParkingPerAppStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        AppSEM appSEM = new AppSEM(ParkingMode.MANUAL);

        double maxHours = appSEM.getMaxHours("1234567899");

        Assertions.assertEquals(4, maxHours);
    }

    @Test
    public void calculateMaxWithZeroBalance() {
        AppSEM appSEM = new AppSEM(ParkingMode.MANUAL);

        double maxHours = appSEM.getMaxHours("1234567891");

        Assertions.assertEquals(0, maxHours);
    }

    @Test
    public void calculateHalfHour() {
        AppSEM appSEM = new AppSEM(ParkingMode.MANUAL);

        double maxHours = appSEM.getMaxHours("1234567899");

        Assertions.assertEquals(0.5, maxHours);
    }

    @Test
    public void startParkingInsufficientBalance() throws InsufficientBalanceException {
        AppSEM appSEM = new AppSEM(ParkingMode.MANUAL);

        /*InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                appSEM.startParking("MBS912", "1130281723"),
                "Expected doThing() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Insufficient balance. Parking not allowed."));*/
    }

    @Test()
    public void startParking() throws InsufficientBalanceException {
        AppSEM appSEM = new AppSEM(ParkingMode.MANUAL);

        LocalTime timeMock = LocalTime.of(12, 0, 0);
        Parking parkingMock = new Parking("MBC645", new ParkingPerAppStrategy(), timeMock);

        when(area.createParking(Mockito.any(), Mockito.any())).thenReturn(parkingMock);
        when(timeUtil.nowTime()).thenReturn(timeMock);

        StartParkingResponse response = appSEM.startParking("MBZ912", "1102931312");

        assertEquals(12, response.getStartHour().getHour());
        assertEquals(0, response.getStartHour().getMinute());
        assertEquals(14, response.getMaxHour());
    }

    @Test
    public void endParking() {
        AppSEM appSEM = new AppSEM(ParkingMode.MANUAL);

        LocalTime dateMock = LocalTime.of(18, 0, 0);
        LocalTime startParkingDateTime = LocalTime.of(16, 0, 0);

        Parking parkingMock = new Parking("MBC645", new ParkingPerAppStrategy(), startParkingDateTime);

        when(area.removeParking(Mockito.any())).thenReturn(parkingMock);
        when(timeUtil.nowTime()).thenReturn(dateMock);

        EndParkingResponse response = appSEM.endParking("1102931312");

        assertEquals(startParkingDateTime, response.getStartHour());
        assertEquals(dateMock, response.getEndHour());
        assertEquals(80, response.getCost());
        assertEquals(2, response.getDuration().getHours());
        assertEquals(0, response.getDuration().getMinutes());
    }

}
