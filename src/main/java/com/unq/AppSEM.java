package com.unq;


import com.unq.alert.AlertManager;
import com.unq.user.Car;
import com.unq.user.Cellphone;

public class AppSEM {
	private Double balance;
	private Cellphone cellphoneAssociated;
	private Car carAssociated;
	private ParkingArea currentArea;
	private AlertManager alertManager;

	public AppSEM(Double balance, Cellphone cellphoneAssociated, Car carAssociated, ParkingArea currentArea) {
		this.balance = balance;
		this.cellphoneAssociated = cellphoneAssociated;
		this.carAssociated = carAssociated;
		this.currentArea = currentArea;
		this.alertManager = new AlertManager();
	}

	public double getMaxHours() {
		return balance / ParkingSystem.PRICE_PER_HOUR;
	}

	public void startParking() {

	}

	public void endParking() {

	}

	public void updateCurrentArea(ParkingArea currentArea) {
		this.currentArea = currentArea;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Cellphone getCellphoneAssociated() {
		return cellphoneAssociated;
	}

	public void setCellphoneAssociated(Cellphone cellphoneAssociated) {
		this.cellphoneAssociated = cellphoneAssociated;
	}

	public Car getCarAssociated() {
		return carAssociated;
	}

	public void setCarAssociated(Car carAssociated) {
		this.carAssociated = carAssociated;
	}

	public ParkingArea getCurrentArea() {
		return currentArea;
	}

	public AlertManager getAlertManager() {
		return alertManager;
	}

	public void setAlertManager(AlertManager alertManager) {
		this.alertManager = alertManager;
	}
}
