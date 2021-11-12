package com.unq.user;

import com.unq.app.sem.AppSEM;

public class Cellphone {

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
}
