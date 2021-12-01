package com.unq.parking;

import com.unq.alert.AlertType;
import com.unq.commons.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.Mockito.*;

public class PointOfSaleTest {

    private ParkingSystem parkingSystem;
    private TimeUtil timeUtil;
    private PointOfSale pointOfSale;
    private ParkingArea parkingArea;

    private final static String PATENT_ASSOCIATED = "NBS963";
    private final static String PHONE_NUMBER = "1234567891";

    @BeforeEach
    public void setUp() {
        parkingSystem = mock(ParkingSystem.class);
        timeUtil = mock(TimeUtil.class);
        parkingArea = mock(ParkingArea.class);

        pointOfSale = new PointOfSale(parkingArea);
        pointOfSale.setParkingSystem(parkingSystem);
        pointOfSale.setTimeUtil(timeUtil);
    }

    @Test
    public void buyCreditTest() {
        pointOfSale.buyCredit(PHONE_NUMBER, 50D);

        verify(parkingSystem).increaseBalance(PHONE_NUMBER, 50D);
        verify(parkingSystem).registryPurchase(Mockito.any());
        verify(parkingSystem).notifyMonitors(
                AlertType.BALANCE_BUY,
                String.format("The number %s made a credit purchase for the value of %s", PHONE_NUMBER, 50D)
        );
    }

    @Test
    public void buyHoursOfParkingTest() {
        when(parkingSystem.getPricePerHour()).thenReturn(40D);
        when(timeUtil.nowTime()).thenReturn(LocalTime.of(15,0));
        when(timeUtil.nowDateTime()).thenReturn(LocalDateTime.of(2021,12,28,15,0));

        pointOfSale.buyHoursOfParking(3, PATENT_ASSOCIATED, PHONE_NUMBER);
        pointOfSale.setArea(parkingArea);

        verify(parkingSystem).reduceBalance(PHONE_NUMBER, 120D);
        verify(parkingSystem).registryPurchase(Mockito.any());
        verify(parkingSystem).notifyMonitors(
                AlertType.START_PARKING,
                String.format("A parking lot for purchase has been created with the patent %s.", PATENT_ASSOCIATED)
        );
    }

}
