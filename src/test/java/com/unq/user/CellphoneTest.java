package com.unq.user;

import com.unq.parking.ParkingArea;
import com.unq.ParkingSystem;
import com.unq.PointOfSale;
import com.unq.alert.AlertManager;
import com.unq.app.sem.AppSEM;
import com.unq.app.sem.Duration;
import com.unq.app.sem.EndParkingResponse;
import com.unq.app.sem.StartParkingResponse;
import com.unq.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CellphoneTest {

    private static final AppSEM appMock = mock(AppSEM.class);
    private static final AlertManager alertManagerMock = mock(AlertManager.class);
    private static final ParkingArea parkingAreaMock = mock(ParkingArea.class);
    private static Cellphone cellphone;

    @BeforeEach
    public void setUp() {
        cellphone = new Cellphone("1234567891", "BMS912");
        cellphone.setApp(appMock);
        cellphone.setCurrentArea(parkingAreaMock);
        cellphone.setAlertManager(alertManagerMock);
    }

    @Test
    public void getBalanceZero() {
        Double balance = cellphone.getBalance();

        Assertions.assertEquals(0, balance);
        verify(alertManagerMock, times(0)).subscribe(Mockito.any(), Mockito.any());
    }

    @Test
    public void getBalance() {
        ParkingSystem.getInstance().increaseBalance("1234567891", 100D);

        Assertions.assertEquals(100, ParkingSystem.getInstance().getBalance("1234567891"));
    }

    @Test
    public void startParkingResponse() throws InsufficientBalanceException {
        String patent = "MBZ912";
        String phoneNumber = "1234567891";
        LocalTime now = LocalTime.now();
        StartParkingResponse response = StartParkingResponse.newBuilder()
                .startHour(now)
                .maxHour(3D)
                .build();

        when(appMock.startParking(patent, phoneNumber)).thenReturn(response);

        StartParkingResponse startParkingResponse = appMock.startParking(patent, phoneNumber);

        Assertions.assertEquals(now, startParkingResponse.getStartHour());
        Assertions.assertEquals(3, startParkingResponse.getMaxHour());
    }

    @Test
    public void endParkingResponse() {
        String phoneNumber = "1234567891";
        LocalTime startHour = LocalTime.now().minusHours(2);
        LocalTime endHour = LocalTime.now();

        EndParkingResponse response = EndParkingResponse.newBuilder()
                .startHour(startHour)
                .endHour(endHour)
                .cost(80D)
                .duration(new Duration(2L,0L))
                .build();

        when(appMock.endParking(phoneNumber)).thenReturn(response);

        EndParkingResponse endParkingResponse = cellphone.endParking();

        Assertions.assertEquals(startHour, endParkingResponse.getStartHour());
        Assertions.assertEquals(endHour, endParkingResponse.getEndHour());
        Assertions.assertEquals(80D, endParkingResponse.getCost());
        Assertions.assertEquals(2, endParkingResponse.getDuration().getHours());
    }

    @Test
    public void buyCreditError() {
        PointOfSale pointOfSaleMock = mock(PointOfSale.class);

        when(parkingAreaMock.getPointOfSales()).thenReturn(List.of());

        Exception exception = assertThrows(Exception.class, () -> {
            cellphone.buyCredit(100D, pointOfSaleMock);
        });

        assertTrue(exception.getMessage().contains("The point of sale does not belong to this parking area."));
    }

    @Test
    public void buyCreditSuccess() throws Exception {
        PointOfSale pointOfSaleMock = mock(PointOfSale.class);

        String phoneNumber = "1234567891";

        when(parkingAreaMock.getPointOfSales()).thenReturn(List.of(pointOfSaleMock));;

        cellphone.buyCredit(100D, pointOfSaleMock);

        verify(pointOfSaleMock, times(1)).buyCredit(phoneNumber, 100D);
    }

}
