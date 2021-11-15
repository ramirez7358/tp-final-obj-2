package com.unq.user;

import com.unq.parking.ParkingArea;
import com.unq.ParkingSystem;
import com.unq.PointOfSale;
import com.unq.alert.AlertListener;
import com.unq.alert.AlertManager;
import com.unq.alert.AlertType;
import com.unq.app.sem.AppSEM;
import com.unq.app.sem.EndParkingResponse;
import com.unq.app.sem.ParkingMode;
import com.unq.app.sem.StartParkingResponse;

public class Cellphone implements AlertListener {

    private ParkingArea currentArea;
    private String phoneNumber;
    private String patentCarAssociated;
    private AppSEM app;
    private AlertManager alertManager;

    public Cellphone(String phoneNumber, String patentCarAssociated) {
        this.phoneNumber = phoneNumber;
        this.patentCarAssociated = patentCarAssociated;
        this.app = new AppSEM(phoneNumber, patentCarAssociated);
        this.alertManager = new AlertManager(AlertType.START_PARKING, AlertType.END_PARKING);

        alertManager.subscribe(AlertType.START_PARKING, this);
        alertManager.subscribe(AlertType.END_PARKING, this);
    }

    public Double getBalance() {
        return ParkingSystem.getInstance().getBalance(phoneNumber);
    }

    public StartParkingResponse startParking() {
        return this.app.startParking();
    }

    public EndParkingResponse endParking() {
        return this.app.endParking();
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

    public void setApp(AppSEM app) {
        this.app = app;
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
