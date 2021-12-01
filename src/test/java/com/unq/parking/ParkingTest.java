package com.unq.parking;

import com.unq.commons.TimeUtil;
import com.unq.purchase.HoursPurchase;
import com.unq.purchase.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParkingTest {

    private Purchase purchaseRegistry;
    private TimeUtil timeUtil;
    private ParkingSystem parkingSystem;

    private final static String PATENT_ASSOCIATED = "NBS963";
    private final static String PHONE_NUMBER = "1234567891";

    @BeforeEach
    public void setUp() {
        purchaseRegistry = mock(HoursPurchase.class);
        timeUtil = mock(TimeUtil.class);
        parkingSystem = mock(ParkingSystem.class);
    }

    @Test
    public void parkingPerPurchaseTest() {
        ParkingPerPurchase parking = new ParkingPerPurchase(PATENT_ASSOCIATED, LocalTime.of(12,30), LocalTime.of(15,30), purchaseRegistry);

        when(timeUtil.nowTime()).thenReturn(LocalTime.of(12,30));

        assertEquals(LocalTime.of(15,30), parking.timeLimit());
        assertEquals(PATENT_ASSOCIATED, parking.getCarPatent());
        assertEquals(purchaseRegistry, parking.getPurchaseRegistry());
    }

    @Test
    public void parkingPerPurchaseInForceTest() {
        ParkingPerPurchase parking = new ParkingPerPurchase(PATENT_ASSOCIATED, LocalTime.of(18,30), LocalTime.of(19,30), purchaseRegistry);
        parking.setTimeUtil(timeUtil);

        when(timeUtil.nowTime()).thenReturn(LocalTime.of(19,0));

        assertTrue(parking.inForce());
    }

    @Test
    public void parkingPerPurchaseInForceLastMinute() {
        ParkingPerPurchase parking = new ParkingPerPurchase(PATENT_ASSOCIATED, LocalTime.of(18,30), LocalTime.of(19,30), purchaseRegistry);
        parking.setTimeUtil(timeUtil);

        when(timeUtil.nowTime()).thenReturn(LocalTime.of(19,29));

        assertTrue(parking.inForce());
    }

    @Test
    public void parkingPerPurchaseOutOfTime() {
        ParkingPerPurchase parking = new ParkingPerPurchase(PATENT_ASSOCIATED, LocalTime.of(12,30), LocalTime.of(14,30), purchaseRegistry);
        parking.setTimeUtil(timeUtil);

        when(timeUtil.nowTime()).thenReturn(LocalTime.of(18,0));

        assertFalse(parking.inForce());
    }

    @Test
    public void parkingPerAppTest() {
        ParkingPerApp parking = new ParkingPerApp(PATENT_ASSOCIATED, LocalTime.of(12,0), PHONE_NUMBER);
        parking.setParkingSystem(parkingSystem);

        when(parkingSystem.getEndTime()).thenReturn(LocalTime.of(20,0));

        assertEquals(LocalTime.of(20,0), parking.timeLimit());
        assertEquals(PATENT_ASSOCIATED, parking.getCarPatent());
        verify(parkingSystem, times(1)).getEndTime();
    }

    @Test
    public void parkingPerAppOutOfTime() {
        ParkingPerApp parking = new ParkingPerApp(PATENT_ASSOCIATED, LocalTime.of(12,0), PHONE_NUMBER);
        parking.setParkingSystem(parkingSystem);

        when(timeUtil.nowTime()).thenReturn(LocalTime.of(18,0));
        when(parkingSystem.getEndTime()).thenReturn(LocalTime.of(20,0));

        assertFalse(parking.inForce());
    }

}
