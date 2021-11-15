package com.unq.app.sem.mode;

import com.unq.alert.AlertType;
import com.unq.app.sem.AppSEM;

public class ManualModeStrategy implements ModeStrategy{
    @Override
    public void manageStartParking(AppSEM app) {
        app.getAlertManager().notify(AlertType.START_PARKING, "You haven't started the parking lot.");
    }

    @Override
    public void manageEndParking(AppSEM app) {
        app.getAlertManager().notify(AlertType.END_PARKING, "You haven't finished parking.");
    }
}
