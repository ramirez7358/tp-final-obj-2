package com.unq.app.sem;

import com.unq.app.sem.movement.DrivingState;
import com.unq.app.sem.movement.MovementState;
import com.unq.app.sem.movement.WalkingState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class MovementTest {

    private static MovementState walkingState;
    private static MovementState drivingState;
    private static AppSEM app;

    @BeforeEach
    public void setUp() {
        walkingState = new WalkingState();
        drivingState = new DrivingState();
        app = mock(AppSEM.class);
    }

    @Test
    public void walkingStateStartDriving() {
        walkingState.startDriving(app);

        verify(app, times(1)).setMovementState(Mockito.any());
        verify(app, times(1)).manageEndParking();
    }

    @Test
    public void walkingStateStartWalkingNoInteractions() {
        walkingState.startWalking(app);

        verifyNoInteractions(app);
    }

    @Test
    public void drivingStateStartWalking() {
        drivingState.startWalking(app);

        verify(app, times(1)).setMovementState(Mockito.any());
        verify(app, times(1)).manageStartParking();
    }

    @Test
    public void drivingStateStartDrivingNoInteractions() {
        drivingState.startDriving(app);

        verifyNoInteractions(app);
    }

}
