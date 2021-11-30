package com.unq.alert;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.unq.alert.AlertType.END_PARKING;
import static com.unq.alert.AlertType.START_PARKING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlertManagerTest {

    @Test
    public void verifyZeroEvents() {
        AlertManager alertManager = new AlertManager();

        assertEquals(0, alertManager.getListeners().size());
    }

    @Test
    public void verifyTwoEvents() {
        AlertManager alertManager = new AlertManager(START_PARKING, END_PARKING);

        assertEquals(2, alertManager.getListeners().size());
    }

    @Test
    public void verifyNotRepeatEvents() {
        AlertManager alertManager = new AlertManager(START_PARKING, START_PARKING);

        assertEquals(1, alertManager.getListeners().size());
    }

    @Test
    public void subscribeTest() {
        AlertListener listener = mock(AlertListener.class);
        AlertManager alertManager = new AlertManager(START_PARKING, END_PARKING);
        alertManager.subscribe(START_PARKING, listener);

        List<AlertListener> listenersStartParking = alertManager.getListeners().get(START_PARKING);

        assertTrue(listenersStartParking.contains(listener));
    }

    @Test
    public void unsubscribeTest() {
        AlertListener listener = mock(AlertListener.class);
        AlertManager alertManager = new AlertManager(START_PARKING, END_PARKING);
        alertManager.subscribe(START_PARKING, listener);
        alertManager.subscribe(END_PARKING, listener);

        List<AlertListener> listenersStartParking = alertManager.getListeners().get(START_PARKING);
        List<AlertListener> listenersEndParking = alertManager.getListeners().get(START_PARKING);

        assertTrue(listenersStartParking.contains(listener));
        assertTrue(listenersEndParking.contains(listener));

        alertManager.unsubscribe(START_PARKING, listener);
        alertManager.unsubscribe(END_PARKING, listener);

        assertFalse(listenersStartParking.contains(listener));
        assertFalse(listenersEndParking.contains(listener));
    }

    @Test
    public void notifyListenersTest() {
        AlertListener listener = mock(AlertListener.class);
        AlertManager alertManager = new AlertManager(START_PARKING, END_PARKING);
        alertManager.subscribe(START_PARKING, listener);
        alertManager.subscribe(END_PARKING, listener);

        alertManager.notify(START_PARKING, "Start parking");

        verify(listener, times(1)).update(START_PARKING, "Start parking");
    }

}
