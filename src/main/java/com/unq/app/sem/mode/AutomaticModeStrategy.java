package com.unq.app.sem.mode;

import com.unq.alert.AlertType;
import com.unq.app.sem.AppSEM;

public class AutomaticModeStrategy implements ModeStrategy{
    @Override
    public void manageStartParking(AppSEM app) {
        app.startParking();
        app.sendNotification(AlertType.START_PARKING, "The parking start has been triggered automatically.");
    }

    @Override
    public void manageEndParking(AppSEM app) {
        app.endParking();
        app.sendNotification(AlertType.END_PARKING, "The end of parking has been triggered automatically.");
    }
}
