package com.unq.app.sem;

import com.unq.ParkingArea;
import com.unq.TimeUtil;
import com.unq.app.sem.mode.ManualStrategy;
import com.unq.exceptions.InsufficientBalanceException;
import com.unq.parking.Parking;
import com.unq.parking.ParkingPerAppStrategy;
import com.unq.user.Car;
import com.unq.user.Cellphone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppSEMTest {

    private static Cellphone cellphone;
    private static Car car;
    private static ParkingArea area;
    private static TimeUtil timeUtil;

    @BeforeAll
    public static void setUp() {
        cellphone = mock(Cellphone.class);
        car = mock(Car.class);
        area = mock(ParkingArea.class);
        timeUtil = mock(TimeUtil.class);
    }

    @Test
    public void calculateMaxHours() {
        AppSEM appSEM = new AppSEM(160D, cellphone, car, area, new ManualStrategy());

        double maxHours = appSEM.getMaxHours();

        Assertions.assertEquals(4, maxHours);
    }

    @Test
    public void calculateMaxWithZeroBalance() {
        AppSEM appSEM = new AppSEM(0D, cellphone, car, area, new ManualStrategy());

        double maxHours = appSEM.getMaxHours();

        Assertions.assertEquals(0, maxHours);
    }

    @Test
    public void calculateHalfHour() {
        AppSEM appSEM = new AppSEM(20D, cellphone, car, area, new ManualStrategy());

        double maxHours = appSEM.getMaxHours();

        Assertions.assertEquals(0.5, maxHours);
    }

    @Test
    public void startParkingInsufficientBalance() {
        AppSEM appSEM = new AppSEM(0D, cellphone, car, area, new ManualStrategy());

        InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                appSEM::startParking,
                "Expected doThing() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Insufficient balance. Parking not allowed."));
    }

    @Test()
    public void startParking() throws InsufficientBalanceException {
        AppSEM appSEM = new AppSEM(80D, cellphone, car, area, new ManualStrategy());
        appSEM.setTimeUtil(timeUtil);

        LocalDateTime dateMock = LocalDateTime.of(2021, 12, 28, 12, 0, 0);
        Parking parkingMock = new Parking("MBC645", new ParkingPerAppStrategy(), dateMock);

        when(area.createParking(Mockito.any(), Mockito.any())).thenReturn(parkingMock);
        when(timeUtil.now()).thenReturn(dateMock);

        StartParkingResponse response = appSEM.startParking();

        assertEquals(12, response.getStartHour().getHour());
        assertEquals(0, response.getStartHour().getMinute());
        assertEquals(14, response.getMaxHour());
    }

    @Test
    public void endParking() {
        AppSEM appSEM = new AppSEM(90D, cellphone, car, area, new ManualStrategy());
        appSEM.setTimeUtil(timeUtil);

        LocalDateTime dateMock = LocalDateTime.of(2021, 12, 28, 18, 0, 0);
        LocalDateTime startParkingDateTime = LocalDateTime.of(2021, 12, 28, 16, 0, 0);

        Parking parkingMock = new Parking("MBC645", new ParkingPerAppStrategy(), startParkingDateTime);

        when(area.removeParking(Mockito.any())).thenReturn(parkingMock);
        when(timeUtil.now()).thenReturn(dateMock);

        EndParkingResponse response = appSEM.endParking();

        assertEquals(startParkingDateTime, response.getStartHour());
        assertEquals(dateMock, response.getEndHour());
        assertEquals(80, response.getCost());
        assertEquals(2, response.getDuration().getHours());
        assertEquals(0, response.getDuration().getMinutes());
        assertEquals(10, appSEM.getBalance());
    }

}
