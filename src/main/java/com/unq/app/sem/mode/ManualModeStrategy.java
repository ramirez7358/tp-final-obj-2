package com.unq.app.sem.mode;

import com.unq.app.sem.AppSEM;

public class ManualModeStrategy implements ModeStrategy{
    @Override
    public void manageStartParking(AppSEM app) {
        app.sendNotification("You haven't started the parking lot.");
    }

    @Override
    public void manageEndParking(AppSEM app) {
        app.sendNotification("You haven't finished parking.");
    }
}
