package com.unq.parking;

import com.unq.alert.AlertManager;
import com.unq.app.inspector.Inspector;
import com.unq.commons.TimeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class ParkingAreaTest {

    private ParkingArea area;
    private Inspector inspector;
    private TimeUtil timeUtil;
    private AlertManager alertManager;

    @BeforeEach
    public void setUp() {
        inspector = mock(Inspector.class);
        timeUtil = mock(TimeUtil.class);
        alertManager = mock(AlertManager.class);
        area = new ParkingArea(inspector, alertManager);
        area.setTimeUtil(timeUtil);
    }

    @Test
    public void existParkingOk() {
        Parking parking1 = mock(Parking.class);
        Parking parking2 = mock(Parking.class);
        area.createParking("1", parking1);
        area.createParking("2", parking2);

        Assertions.assertTrue(area.existParking("1"));
        Assertions.assertTrue(area.existParking("2"));
        Assertions.assertFalse(area.existParking("3"));
    }

}
