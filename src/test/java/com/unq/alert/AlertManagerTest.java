package com.unq.alert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.unq.alert.AlertType.END_PARKING;
import static com.unq.alert.AlertType.START_PARKING;

public class AlertManagerTest {

    @Test
    public void verifyZeroEvents() {
        AlertManager alertManager = new AlertManager();

        Assertions.assertEquals(0, alertManager.getListeners().size());
    }

    @Test
    public void verifyTwoEvents() {
        AlertManager alertManager = new AlertManager(START_PARKING, END_PARKING);

        Assertions.assertEquals(2, alertManager.getListeners().size());
    }

    @Test
    public void verifyNotRepeatEvents() {
        AlertManager alertManager = new AlertManager(START_PARKING, START_PARKING);

        Assertions.assertEquals(1, alertManager.getListeners().size());
    }

}
