package com.unq.app.sem.mode;

import com.unq.app.sem.AppSEM;

public interface ModeStrategy {
    public void manageStartParking(AppSEM app);
    public void manageEndParking(AppSEM app);
}
