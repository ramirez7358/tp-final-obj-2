package com.unq.user;

import com.unq.ParkingArea;
import com.unq.PointOfSale;
import com.unq.alert.AlertListener;
import com.unq.alert.AlertManager;
import com.unq.alert.AlertType;
import com.unq.app.sem.AppSEM;
import com.unq.app.sem.EndParkingResponse;
import com.unq.app.sem.StartParkingResponse;
import com.unq.app.sem.mode.ManualStrategy;
import com.unq.exceptions.InsufficientBalanceException;

public class Cellphone implements AlertListener {

    private ParkingArea currentArea;
    private String phoneNumber;
    private String patentCarAssociated;
    private final AppSEM app;
    private AlertManager alertManager;

    public Cellphone(String phoneNumber, String patentCarAssociated, AppSEM app) {
        this.phoneNumber = phoneNumber;
        this.patentCarAssociated = patentCarAssociated;
        this.app = new AppSEM(new ManualStrategy());

        alertManager.subscribe(AlertType.START_PARKING, this);
        alertManager.subscribe(AlertType.END_PARKING, this);
    }

    public StartParkingResponse startParking() throws InsufficientBalanceException {
        return this.app.startParking(patentCarAssociated, phoneNumber);
    }

    public EndParkingResponse endParking() {
        return this.app.endParking(phoneNumber);
    }

    public void buyCredit(Double credit, PointOfSale pointOfSale) throws Exception {
        if(!this.currentArea.getPointOfSales().contains(pointOfSale)) {
            throw new Exception("The point of sale does not belong to this parking area.");
        }

        pointOfSale.buyCredit(phoneNumber, credit);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPatentCarAssociated() {
        return patentCarAssociated;
    }

    public void setPatentCarAssociated(String patentCarAssociated) {
        this.patentCarAssociated = patentCarAssociated;
    }

    public AppSEM getApp() {
        return app;
    }

    public AlertManager getAlertManager() {
        return alertManager;
    }

    public void setAlertManager(AlertManager alertManager) {
        this.alertManager = alertManager;
    }

    public ParkingArea getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(ParkingArea currentArea) {
        this.currentArea = currentArea;
    }

    @Override
    public void update(AlertType alertType, String data) {

    }
}
