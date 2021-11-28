package com.unq.purchase;

import com.unq.parking.ParkingArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class PurchaseTest {

    private static ParkingArea parkingArea;

    private final static String PATENT_ASSOCIATED = "NBS963";
    private final static String PHONE_NUMBER = "1234567891";

    @BeforeEach
    public void setUp(){
        parkingArea = mock(ParkingArea.class);
    }

    @Test
    public void balancePurchaseOk() {
        LocalDateTime now = LocalDateTime.now();
        BalancePurchase purchase = new BalancePurchase(parkingArea, now, 40.0, PHONE_NUMBER);

        assertEquals(parkingArea, purchase.getArea());
        assertEquals(now, purchase.getDate());
        assertEquals(40, purchase.getAmount());
        assertEquals(PHONE_NUMBER, purchase.getPhoneNumber());
    }

    @Test
    public void hoursPurchase() {
        LocalDateTime now = LocalDateTime.now();
        HoursPurchase purchase = new HoursPurchase(parkingArea, now, 2, PATENT_ASSOCIATED);

        assertEquals(parkingArea, purchase.getArea());
        assertEquals(now, purchase.getDate());
        assertEquals(2, purchase.getQuantity());
        assertEquals(PATENT_ASSOCIATED, purchase.getPatent());
    }

}
