package com.unq.app.inspector;

import com.unq.commons.TimeUtil;
import com.unq.exceptions.CustomException;
import com.unq.parking.ParkingArea;
import com.unq.parking.ParkingPerApp;
import com.unq.parking.ParkingSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AppInspectorTest {

    private static AppInspector app;
    private static Inspector inspector;
    private static TimeUtil timeUtil;
    private static ParkingSystem parkingSystem;

    private final static String PHONE_NUMBER = "1234567891";
    private final static String PATENT_ASSOCIATED = "NBS963";

    @BeforeEach
    public void setUp() {
        timeUtil = mock(TimeUtil.class);
        parkingSystem = mock(ParkingSystem.class);
        inspector = mock(Inspector.class);
        app = new AppInspector(inspector);
        app.setTimeUtil(timeUtil);
        app.setParkingSystem(parkingSystem);
    }

    @Test
    public void checkParkingValidTrue() {
        ParkingArea area = mock(ParkingArea.class);
        ParkingPerApp parking = mock(ParkingPerApp.class);

        when(inspector.getParkingArea()).thenReturn(area);
        when(area.getParkingByPatent(PATENT_ASSOCIATED)).thenReturn(parking);
        when(parking.inForce()).thenReturn(true);

        Boolean isValid = app.checkParkingValid(PATENT_ASSOCIATED);

        assertEquals(true, isValid);
    }

    @Test
    public void checkParkingValidFalse() {
        ParkingArea area = mock(ParkingArea.class);
        ParkingPerApp parking = mock(ParkingPerApp.class);

        when(inspector.getParkingArea()).thenReturn(area);
        when(area.getParkingByPatent(PATENT_ASSOCIATED)).thenReturn(parking);
        when(parking.inForce()).thenReturn(false);

        Boolean isValid = app.checkParkingValid(PATENT_ASSOCIATED);

        assertEquals(false, isValid);
    }

    @Test
    public void checkParkingNotFound() {
        ParkingArea area = mock(ParkingArea.class);

        when(inspector.getParkingArea()).thenReturn(area);
        when(area.getParkingByPatent(Mockito.any()))
                .thenThrow(new CustomException.ParkingNotFound(String.format("No parking associated with the patent was found: %s", PATENT_ASSOCIATED)));

        CustomException.ParkingNotFound thrown = assertThrows(
                CustomException.ParkingNotFound.class,
                () -> app.checkParkingValid(PATENT_ASSOCIATED)
        );

        assertEquals("No parking associated with the patent was found: NBS963", thrown.getMessage());
    }

    @Test
    public void registryViolationOk() {
        ParkingArea area = mock(ParkingArea.class);
        ParkingPerApp parking = mock(ParkingPerApp.class);

        when(inspector.getParkingArea()).thenReturn(area);
        when(area.getParkingByPatent(PATENT_ASSOCIATED)).thenReturn(parking);
        when(parking.inForce()).thenReturn(false);
        when(timeUtil.nowDateTime()).thenReturn(LocalDateTime.of(2021,11,1,12,0));

        Violation violation = app.registryViolation(PATENT_ASSOCIATED);

        verify(parkingSystem, times(1)).registryViolation(Mockito.any());
        assertEquals(PATENT_ASSOCIATED, violation.getPatent());
        assertEquals(LocalDateTime.of(2021,11,1,12,0), violation.getDateTime());
        assertEquals(inspector, violation.getInspector());
        assertEquals(area, violation.getParkingArea());
    }

    @Test
    public void registryViolationWithValidParking() {
        ParkingArea area = mock(ParkingArea.class);
        ParkingPerApp parking = mock(ParkingPerApp.class);

        when(inspector.getParkingArea()).thenReturn(area);
        when(area.getParkingByPatent(PATENT_ASSOCIATED)).thenReturn(parking);
        when(parking.inForce()).thenReturn(true);

        CustomException.InspectionException thrown = assertThrows(
                CustomException.InspectionException.class,
                () -> app.registryViolation(PATENT_ASSOCIATED)
        );

        assertEquals("The patent has a valid parking.", thrown.getMessage());
    }

    @Test
    public void testConstructOfInspector() {
        ParkingArea area = mock(ParkingArea.class);
        ParkingArea area2 = mock(ParkingArea.class);
        Inspector newInspector = new Inspector("Brian", "Ramirez", "54621321", area);

        assertEquals("Brian", newInspector.getName());
        assertEquals("Ramirez", newInspector.getLastName());
        assertEquals("54621321", newInspector.getDni());
        assertEquals(area, newInspector.getParkingArea());

        newInspector.setParkingArea(area2);
        assertEquals(area2, newInspector.getParkingArea());
    }

}
