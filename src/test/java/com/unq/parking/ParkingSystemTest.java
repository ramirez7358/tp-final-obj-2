package com.unq.parking;

import com.unq.alert.AlertListener;
import com.unq.alert.AlertManager;
import com.unq.alert.AlertType;
import com.unq.app.inspector.Violation;
import com.unq.commons.TimeUtil;
import com.unq.exceptions.CustomException;
import com.unq.purchase.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ParkingSystemTest {

    private ParkingSystem parkingSystem;
    private AlertManager alertManager;
    private TimeUtil timeUtil;

    private final static String PATENT_ASSOCIATED = "NBS963";
    private final static String PHONE_NUMBER = "1234567891";

    @BeforeEach
    public void setUp() {
        alertManager = mock(AlertManager.class);
        timeUtil = mock(TimeUtil.class);

        parkingSystem = ParkingSystem.getInstance();
        parkingSystem.setAlertManager(alertManager);
        parkingSystem.setTimeUtil(timeUtil);
    }

    @Test
    public void subscribeToAlertsOk() {
        AlertListener listener = mock(AlertListener.class);

        parkingSystem.subscribeParkingMonitoring(listener);

        verify(alertManager).subscribe(AlertType.START_PARKING, listener);
        verify(alertManager).subscribe(AlertType.END_PARKING, listener);
        verify(alertManager).subscribe(AlertType.BALANCE_BUY, listener);
    }

    @Test
    public void unsubscribeToAlertsOk() {
        AlertListener listener = mock(AlertListener.class);

        parkingSystem.unsubscribeParkingMonitoring(listener);

        verify(alertManager).unsubscribe(AlertType.START_PARKING, listener);
        verify(alertManager).unsubscribe(AlertType.END_PARKING, listener);
        verify(alertManager).unsubscribe(AlertType.BALANCE_BUY, listener);
    }

    @Test
    public void notifyMonitorsOk() {
        parkingSystem.notifyMonitors(AlertType.START_PARKING, "Start parking");
        parkingSystem.notifyMonitors(AlertType.BALANCE_BUY, "Buy balance");

        verify(alertManager).notify(AlertType.START_PARKING, "Start parking");
        verify(alertManager).notify(AlertType.BALANCE_BUY, "Buy balance");
    }

    @Test
    public void registryPurchaseOk() {
        Purchase purchase = mock(Purchase.class);

        parkingSystem.registryPurchase(purchase);

        assertEquals(1, parkingSystem.getShoppingRecord().size());
    }

    @Test
    public void increaseBalanceOk() {
        parkingSystem.increaseBalance(PHONE_NUMBER, 1000D);

        Double balance = parkingSystem.getBalance(PHONE_NUMBER);

        assertEquals(1000, balance);
    }

    @Test
    public void addBalanceToPhoneNumber() {
        String phoneNumber = "1130381654";
        parkingSystem.increaseBalance(phoneNumber, 1000D);

        Double balance = parkingSystem.getBalance(phoneNumber);

        assertEquals(1000, balance);

        parkingSystem.increaseBalance(phoneNumber, 500D);

        balance = parkingSystem.getBalance(phoneNumber);

        assertEquals(1500, balance);
    }

    @Test
    public void getBalanceOfUnregisteredPhoneNumber() {
        assertEquals(0, parkingSystem.getBalance("1561548618"));
    }

    @Test
    public void reduceBalanceOk() {
        String phoneNumber = "123";
        parkingSystem.increaseBalance(phoneNumber, 200D);

        assertEquals(200, parkingSystem.getBalance(phoneNumber));

        parkingSystem.reduceBalance(phoneNumber, 10D);

        assertEquals(190, parkingSystem.getBalance(phoneNumber));
    }

    @Test
    public void reduceBalanceUserNotFound() {
        CustomException.UserNotFoundException thrown = assertThrows(
                CustomException.UserNotFoundException.class,
                () -> parkingSystem.reduceBalance("999", 10D)
        );

        assertEquals("The user has no registered credit.", thrown.getMessage());
    }

    @Test
    public void getCostTest() {
        assertEquals(80, parkingSystem.getCost(120L));
        assertEquals(36.666666666666664, parkingSystem.getCost(55));
        assertEquals(30, parkingSystem.getCost(45));
    }

    @Test
    public void registryViolation() {
        Violation violation1 = mock(Violation.class);
        Violation violation2 = mock(Violation.class);

        parkingSystem.registryViolation(violation1);
        parkingSystem.registryViolation(violation2);

        assertEquals(2, parkingSystem.getViolations().size());
        assertEquals(Arrays.asList(violation1, violation2), parkingSystem.getViolations());
    }

    @Test
    public void changePricePerHourTest() {
        assertEquals(40, parkingSystem.getPricePerHour());
        parkingSystem.changePricePerHour(100D);
        assertEquals(100, parkingSystem.getPricePerHour());
        parkingSystem.changePricePerHour(40D);
    }

    @Test
    public void changeStartTimeTest() {
        assertEquals(LocalTime.of(7,0), parkingSystem.getStartTime());
        parkingSystem.changeStartTime(LocalTime.of(8,0));
        assertEquals(LocalTime.of(8,0), parkingSystem.getStartTime());
        parkingSystem.changeStartTime(LocalTime.of(7,0));
    }

    @Test
    public void changeEndTimeTest() {
        assertEquals(LocalTime.of(20,0), parkingSystem.getEndTime());
        parkingSystem.changeEndTime(LocalTime.of(19,0));
        assertEquals(LocalTime.of(19,0), parkingSystem.getEndTime());
        parkingSystem.changeEndTime(LocalTime.of(20,0));
    }

    @Test
    public void finalizeAllCurrentParkingInHour() {
        when(timeUtil.nowTime()).thenReturn(LocalTime.of(21,0));
        when(timeUtil.calculateTimeFrom(any())).thenReturn(120L);

        parkingSystem.increaseBalance("1", 100D);
        assertEquals(100, parkingSystem.getBalance("1"));

        ParkingArea area1 = mock(ParkingArea.class);
        ParkingArea area2 = mock(ParkingArea.class);
        ParkingArea area3 = mock(ParkingArea.class);

        Parking parking1 = mock(Parking.class);
        Parking parking2 = mock(Parking.class);
        Parking parking3 = mock(Parking.class);
        Parking parking4 = mock(Parking.class);
        Parking parking5 = mock(Parking.class);

        when(area1.getParkings()).thenReturn(Map.of("1",parking1));
        when(area2.getParkings()).thenReturn(Map.of("2", parking2, "3", parking3));
        when(area3.getParkings()).thenReturn(Map.of("4", parking4, "5", parking5));
        when(area1.removeParking("1")).thenReturn(parking1);
        when(parking1.inForce()).thenReturn(true);
        when(parking2.inForce()).thenReturn(false);
        when(parking3.inForce()).thenReturn(false);
        when(parking4.inForce()).thenReturn(false);
        when(parking5.inForce()).thenReturn(false);

        parkingSystem.addParkingArea(area1);
        parkingSystem.addParkingArea(area2);
        parkingSystem.addParkingArea(area3);

        parkingSystem.finalizeAllCurrentParking();

        assertEquals(20, parkingSystem.getBalance("1"));
        verify(area1, times(1)).getParkings();
        verify(area2, times(1)).getParkings();
        verify(area3, times(1)).getParkings();
    }

}
