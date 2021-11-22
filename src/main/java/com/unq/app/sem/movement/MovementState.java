package com.unq.app.sem.movement;

import com.unq.app.sem.AppSEM;

public interface MovementState {
    public void startDriving(AppSEM app);
    public void startWalking(AppSEM app);
}
