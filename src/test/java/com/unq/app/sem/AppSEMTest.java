package com.unq.app.sem;

import com.unq.ParkingArea;
import com.unq.user.Car;
import com.unq.user.Cellphone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class AppSEMTest {

    private static Cellphone cellphone;
    private static Car car;
    private static ParkingArea area;

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

}
