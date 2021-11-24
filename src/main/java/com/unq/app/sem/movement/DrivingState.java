package com.unq.app.sem.movement;

import com.unq.app.sem.AppSEM;

public class DrivingState implements MovementState {
    @Override
    public void startDriving(AppSEM app) {

    }

    @Override
    public void startWalking(AppSEM app) {
        app.setMovementState(new WalkingState());
        app.manageEndParking();
    }
}
