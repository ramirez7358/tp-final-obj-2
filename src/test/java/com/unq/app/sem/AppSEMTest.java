package com.unq.app.sem;

import com.unq.ParkingArea;
import com.unq.exceptions.InsufficientBalanceException;
import com.unq.parking.Parking;
import com.unq.user.Car;
import com.unq.user.Cellphone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppSEMTest {

    private static Cellphone cellphone;
    private static Car car;
    private static ParkingArea area;

    @Mock
    private LocalDateTime localDateTime;

    @BeforeAll
    public static void setUp() {
        cellphone = mock(Cellphone.class);
        car = mock(Car.class);
        area = mock(ParkingArea.class);
    }

    @Test
    public void calculateMaxHours() {
        AppSEM appSEM = new AppSEM(160D, cellphone, car, area);

        double maxHours = appSEM.getMaxHours();

        Assertions.assertEquals(4, maxHours);
    }

    @Test
    public void calculateMaxWithZeroBalance() {
        AppSEM appSEM = new AppSEM(0D, cellphone, car, area);

        double maxHours = appSEM.getMaxHours();

        Assertions.assertEquals(0, maxHours);
    }

    @Test
    public void calculateHalfHour() {
        AppSEM appSEM = new AppSEM(20D, cellphone, car, area);

        double maxHours = appSEM.getMaxHours();

        Assertions.assertEquals(0.5, maxHours);
    }

    @Test
    public void startParkingInsufficientBalance() {
        AppSEM appSEM = new AppSEM(0D, cellphone, car, area);

        InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                appSEM::startParking,
                "Expected doThing() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Insufficient balance. Parking not allowed."));
    }

    /**
     * @TODO Este test fallaria cuando estamos fuera de los horarios permitidos de parking
     *  ¿Como mockeo la hora en la que estoy en este momento?
     * */
    @Disabled
    @Test()
    public void startParking() throws InsufficientBalanceException {
        AppSEM appSEM = new AppSEM(80D, cellphone, car, area);
        LocalDateTime dateMock = LocalDateTime.of(2021, 12, 28, 12, 0, 0);

        when(area.createParking(Mockito.any(), Mockito.any())).thenReturn(null);

        StartParkingResponse response = appSEM.startParking();

        assertEquals(LocalDateTime.now().getHour(), response.getStartHour().getHour());
        assertEquals(LocalDateTime.now().getMinute(), response.getStartHour().getMinute());
        assertEquals(LocalDateTime.now().getHour() + 2, response.getMaxHour());
    }

    @Test
    public void test() {
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), LocalDateTime.now().plusHours(2).plusMinutes(5));

        System.out.println(minutes / 60);
    }

}
