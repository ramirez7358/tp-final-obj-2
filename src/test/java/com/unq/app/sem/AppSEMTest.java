package com.unq.app.sem;

import com.unq.alert.AlertType;
import com.unq.app.sem.activities.EndParkingResponse;
import com.unq.app.sem.activities.StartParkingResponse;
import com.unq.app.sem.mode.AutomaticModeStrategy;
import com.unq.app.sem.mode.ManualModeStrategy;
import com.unq.app.sem.movement.DrivingState;
import com.unq.app.sem.movement.MovementState;
import com.unq.app.sem.movement.WalkingState;
import com.unq.exceptions.CustomException;
import com.unq.parking.ParkingArea;
import com.unq.commons.TimeUtil;
import com.unq.parking.ParkingPerApp;
import com.unq.parking.ParkingSystem;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppSEMTest {

    private static MovementState drivingState;
    private static MovementState walkingState;
    private static ManualModeStrategy manualModeStrategy;
    private static AutomaticModeStrategy automaticModeStrategy;
    private static ParkingArea parkingArea;
    private static TimeUtil timeUtil;
    private static ParkingSystem parkingSystem;
    private static AppSEM app;

    private final static String PHONE_NUMBER = "1234567891";
    private final static String PATENT_ASSOCIATED = "NBS963";

    @BeforeEach
    public void setUp() {
        drivingState = mock(DrivingState.class);
        walkingState = mock(WalkingState.class);
        timeUtil = mock(TimeUtil.class);
        manualModeStrategy = mock(ManualModeStrategy.class);
        automaticModeStrategy = mock(AutomaticModeStrategy.class);
        parkingArea = mock(ParkingArea.class);
        parkingSystem = mock(ParkingSystem.class);

        app = new AppSEM(PHONE_NUMBER, PATENT_ASSOCIATED, drivingState);
        app.setAppMode(manualModeStrategy);
        app.setCurrentArea(parkingArea);
        app.setParkingSystem(parkingSystem);
        app.setTimeUtil(timeUtil);
    }

    @Test
    public void getMaxHourWithCredit() {
        when(parkingSystem.getBalance(PHONE_NUMBER)).thenReturn(120D);
        when(parkingSystem.getPricePerHour()).thenReturn(40D);

        double maxHours = app.getMaxHours();

        Assertions.assertEquals(3, maxHours);
    }

    @Test
    public void getMaxHourWithOutCredit() {
        when(parkingSystem.getBalance(PHONE_NUMBER)).thenReturn(0D);
        when(parkingSystem.getPricePerHour()).thenReturn(40D);

        double maxHours = app.getMaxHours();

        assertEquals(0, maxHours);
    }

    @Test
    public void getBalanceOfNewPhoneNumber() {
        AppSEM newApp = new AppSEM("1136598741", PATENT_ASSOCIATED, drivingState);
        app.setParkingSystem(parkingSystem);

        double balance = newApp.getBalance();

        assertEquals(0, balance);
    }

    @Test
    public void getBalanceInZero() {
        when(parkingSystem.getBalance(PHONE_NUMBER)).thenReturn(200D);

        double balance = app.getBalance();

        assertEquals(200D, balance);
    }

    @Test
    public void manageStartParkingManualMode() {
        app.manageStartParking();
        verify(manualModeStrategy, times(1)).manageStartParking(app);
        verifyNoInteractions(automaticModeStrategy);
    }

    @Test
    public void manageEndParkingManualMode() {
        app.manageEndParking();
        verify(manualModeStrategy, times(1)).manageEndParking(app);
        verifyNoInteractions(automaticModeStrategy);
    }

    @Test
    public void changeAppModeAndManageStartParking() {
        app.setAppMode(automaticModeStrategy);
        app.manageStartParking();

        verify(automaticModeStrategy, times(1)).manageStartParking(app);
        verifyNoInteractions(manualModeStrategy);
        assertEquals(automaticModeStrategy, app.getAppMode());
    }

    @Test
    public void changeAppModeAndManageEndParking() {
        app.setAppMode(automaticModeStrategy);
        app.manageEndParking();

        verify(automaticModeStrategy, times(1)).manageEndParking(app);
        verifyNoInteractions(manualModeStrategy);
        assertEquals(automaticModeStrategy, app.getAppMode());
    }

    @Test
    public void startParkingInvalidBalance() {
        when(parkingSystem.getBalance(PHONE_NUMBER)).thenReturn(0D);

        CustomException.InsufficientBalanceException thrown = assertThrows(
                CustomException.InsufficientBalanceException.class,
                () -> app.startParking()
        );

        assertEquals("Insufficient balance. Parking not allowed.", thrown.getMessage());
    }

    @Test
    public void startParkingInvalidHour() {
        LocalTime parkingStartDate = LocalTime.of(7, 0);
        LocalTime parkingEndDate = LocalTime.of(20, 0);

        when(parkingSystem.getBalance(PHONE_NUMBER)).thenReturn(80D);
        when(parkingSystem.getStartTime()).thenReturn(parkingStartDate);
        when(parkingSystem.getEndTime()).thenReturn(parkingEndDate);
        when(timeUtil.nowTime()).thenReturn(LocalTime.of(22,30));

        CustomException.HourOutOfRangeException thrown = assertThrows(
                CustomException.HourOutOfRangeException.class,
                () -> app.startParking()
        );

        String expectedMessage = String.format(
                "It is not possible to generate a parking lot outside the time range %s - %s",
                parkingStartDate,
                parkingEndDate
        );

        assertEquals(expectedMessage, thrown.getMessage());
    }

    @Test
    public void startParkingOk() {
        LocalTime nowMock = LocalTime.of(17, 30);

        when(timeUtil.nowTime()).thenReturn(nowMock);
        when(parkingSystem.getBalance(PHONE_NUMBER)).thenReturn(80D);
        when(parkingSystem.getStartTime()).thenReturn(LocalTime.of(7,0));
        when(parkingSystem.getEndTime()).thenReturn(LocalTime.of(20,0));
        when(parkingSystem.getPricePerHour()).thenReturn(40D);

        app.startParking();

        String expectedMessage = "Start parking with the following information: Start hour: 17:30, Max hours: 19:30";

        verify(parkingArea, times(1)).createParking(Mockito.any(), Mockito.any());
        verify(parkingSystem, times(1)).notifyMonitors(AlertType.START_PARKING, expectedMessage);
        assertEquals(1, app.getActivityHistory().size());
        assertEquals(StartParkingResponse.class, app.getActivityHistory().get(0).getClass());
        assertEquals(expectedMessage, app.getActivityHistory().get(0).message());
    }

    @Test
    public void endParkingOk() {
        ParkingPerApp parking = new ParkingPerApp(PATENT_ASSOCIATED, PHONE_NUMBER);
        parking.setTimeUtil(timeUtil);
        parking.setParkingSystem(parkingSystem);
        parking.setCreationTime(LocalTime.of(9,0));

        when(parkingArea.removeParking(PHONE_NUMBER)).thenReturn(parking);
        when(timeUtil.nowTime()).thenReturn(LocalTime.of(12,0));
        when(parkingSystem.getPricePerHour()).thenReturn(40D);

        String expectedMessage = "End parking with the following information: Start hour: 09:00, End hour: 12:00, Duration: 3:0, Cost:120.0";

        app.endParking();

        verify(parkingSystem, times(1)).notifyMonitors(AlertType.END_PARKING, expectedMessage);
        assertEquals(1, app.getActivityHistory().size());
        assertEquals(EndParkingResponse.class, app.getActivityHistory().get(0).getClass());
        verify(parkingSystem).reduceBalance(PHONE_NUMBER, 120D);
    }

    @Test
    public void verifyDriving() {
        app.driving();

        verify(drivingState, times(1)).startDriving(app);
    }

    @Test
    public void verifyWalking() {
        app.walking();

        verify(drivingState, times(1)).startWalking(app);
    }

    @Test
    public void changeStateOfApp() {
        app.setMovementState(walkingState);

        assertEquals(walkingState, app.getMovementState());
    }

}
