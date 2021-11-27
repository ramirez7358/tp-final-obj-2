package com.unq.app.sem;

import com.unq.alert.AlertType;
import com.unq.app.sem.mode.AutomaticModeStrategy;
import com.unq.app.sem.mode.ManualModeStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ModesTest {

    private static ManualModeStrategy manualModeStrategy;
    private static AutomaticModeStrategy automaticModeStrategy;
    private static AppSEM app;

    @BeforeEach
    public void setUp() {
        manualModeStrategy = new ManualModeStrategy();
        automaticModeStrategy = new AutomaticModeStrategy();
        app = mock(AppSEM.class);
    }

    @Test
    public void automaticModeStrategyManageStartParking() {
        automaticModeStrategy.manageStartParking(app);

        verify(app, times(1)).startParking();
        verify(app, times(1)).sendNotification(AlertType.START_PARKING, "The parking start has been triggered automatically.");
    }

    @Test
    public void automaticModeStrategyManageEndParking() {
        automaticModeStrategy.manageEndParking(app);

        verify(app, times(1)).endParking();
        verify(app, times(1)).sendNotification(AlertType.END_PARKING, "The end of parking has been triggered automatically.");
    }

    @Test
    public void manualModeStrategyManageStartParking() {
        manualModeStrategy.manageStartParking(app);

        verify(app, times(1)).sendNotification(AlertType.START_PARKING, "You haven't started the parking lot.");
    }

    @Test
    public void manualModeStrategyManageEndParking() {
        manualModeStrategy.manageEndParking(app);

        verify(app, times(1)).sendNotification(AlertType.END_PARKING, "You haven't finished parking.");
    }
}
