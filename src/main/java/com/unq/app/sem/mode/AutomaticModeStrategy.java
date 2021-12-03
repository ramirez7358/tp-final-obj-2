package com.unq.app.sem.mode;

import com.unq.app.sem.AppSEM;

public class AutomaticModeStrategy implements ModeStrategy{
    @Override
    public void manageStartParking(AppSEM app) {
        app.startParking();
        app.sendNotification("The parking start has been triggered automatically.");
    }

    @Override
    public void manageEndParking(AppSEM app) {
        app.endParking();
        app.sendNotification("The end of parking has been triggered automatically.");
    }
}
