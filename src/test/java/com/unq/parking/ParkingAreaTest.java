package com.unq.parking;

import com.unq.app.inspector.Inspector;
import com.unq.exceptions.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ParkingAreaTest {

    private ParkingArea area;
    private Inspector inspector;

    private final static String PATENT_ASSOCIATED = "NBS963";
    private final static String PHONE_NUMBER = "1234567891";

    @BeforeEach
    public void setUp() {
        inspector = mock(Inspector.class);
        area = new ParkingArea(inspector);
    }

    @Test
    public void containPointOfSaleTrue() {
        PointOfSale pointOfSale = mock(PointOfSale.class);
        area.addPointOfSale(pointOfSale);

        assertTrue(area.containPoint(pointOfSale));
    }

    @Test
    public void containPointOfSaleFalse() {
        PointOfSale pointOfSale = mock(PointOfSale.class);

        assertFalse(area.containPoint(pointOfSale));
    }
    
    @Test
    public void getPhoneNumberByPatentTest() {
        ParkingPerPurchase parking = mock(ParkingPerPurchase.class);

        area.createParking(PHONE_NUMBER, parking);

        when(parking.getCarPatent()).thenReturn(PATENT_ASSOCIATED);

        String phoneNumber = area.getPhoneNumberByPatent(PATENT_ASSOCIATED);

        assertEquals(PHONE_NUMBER, phoneNumber);
    }

    @Test
    public void getPhoneNumberEmpty() {
        ParkingPerPurchase parking = mock(ParkingPerPurchase.class);

        area.createParking(PHONE_NUMBER, parking);

        when(parking.getCarPatent()).thenReturn(PATENT_ASSOCIATED);

        CustomException.ParkingNotFound thrown = assertThrows(
                CustomException.ParkingNotFound.class,
                () -> area.getPhoneNumberByPatent("ASD")
        );

        assertEquals("No parking associated with the patent was found", thrown.getMessage());
    }

    @Test
    public void createParkingOk() {
        ParkingPerPurchase parking = mock(ParkingPerPurchase.class);

        area.createParking(PHONE_NUMBER, parking);

        assertTrue(area.existParking(PHONE_NUMBER));
        assertEquals(1, area.getParkings().size());
    }

    @Test
    public void removeParkingOk() {
        ParkingPerPurchase parking = mock(ParkingPerPurchase.class);

        area.createParking(PHONE_NUMBER, parking);

        assertEquals(1, area.getParkings().size());

        area.removeParking(PHONE_NUMBER);

        assertFalse(area.existParking(PHONE_NUMBER));
        assertEquals(0, area.getParkings().size());
    }

    @Test
    public void getParkingByPatentOk() {
        ParkingPerPurchase parking = mock(ParkingPerPurchase.class);

        area.createParking(PHONE_NUMBER, parking);

        when(parking.getCarPatent()).thenReturn(PATENT_ASSOCIATED);

        Parking parkingResponse = area.getParkingByPatent(PATENT_ASSOCIATED);

        assertEquals(parking, parkingResponse);
    }

    @Test
    public void getParkingByPatentEmpty() {
        ParkingPerPurchase parking = mock(ParkingPerPurchase.class);

        area.createParking(PHONE_NUMBER, parking);

        when(parking.getCarPatent()).thenReturn(PATENT_ASSOCIATED);

        CustomException.ParkingNotFound thrown = assertThrows(
                CustomException.ParkingNotFound.class,
                () -> area.getParkingByPatent("ASDASD")
        );

        String messageExpected = "No parking associated with the patent was found: ASDASD";

        assertEquals(messageExpected, thrown.getMessage());
    }

    @Test
    public void existParkingOk() {
        Parking parking1 = mock(Parking.class);
        Parking parking2 = mock(Parking.class);
        area.createParking("1", parking1);
        area.createParking("2", parking2);

        assertTrue(area.existParking("1"));
        assertTrue(area.existParking("2"));
        assertFalse(area.existParking("3"));
    }

    @Test
    public void getOnlyParkingsTest() {
        Parking parking1 = mock(Parking.class);
        Parking parking2 = mock(Parking.class);
        Parking parking3 = mock(Parking.class);
        Parking parking4 = mock(Parking.class);

        area.createParking("1", parking1);
        area.createParking("2", parking2);
        area.createParking("3", parking3);
        area.createParking("4", parking4);

        List<Parking> parkings = area.getAllParkings();

        assertEquals(Arrays.asList(parking1,parking2,parking3,parking4), parkings);
    }

}
