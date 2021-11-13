package com.unq.user;

import com.unq.alert.AlertListener;
import com.unq.alert.AlertType;
import com.unq.app.sem.AppSEM;

public class Cellphone implements AlertListener {

    private String phoneNumber;

    public Cellphone(String phoneNumber, AppSEM app) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void update(AlertType alertType, String data) {

    }
}
