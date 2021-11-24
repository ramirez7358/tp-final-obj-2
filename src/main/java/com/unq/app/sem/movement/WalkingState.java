package com.unq.app.sem.movement;

import com.unq.app.sem.AppSEM;

public class WalkingState implements MovementState{
    @Override
    public void startDriving(AppSEM app) {
        app.setMovementState(new DrivingState());
        app.manageEndParking();
    }

    @Override
    public void startWalking(AppSEM app) {

    }
}
