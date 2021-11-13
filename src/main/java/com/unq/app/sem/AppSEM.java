package com.unq.app.sem;


import com.unq.ParkingArea;
import com.unq.ParkingSystem;
import com.unq.alert.AlertManager;
import com.unq.alert.AlertType;
import com.unq.exceptions.InsufficientBalanceException;
import com.unq.user.Car;
import com.unq.user.Cellphone;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppSEM implements MovementSensor {
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
		this.alertManager = new AlertManager(AlertType.START_PARKING, AlertType.END_PARKING);

		alertManager.subscribe(AlertType.START_PARKING, cellphoneAssociated);
		alertManager.subscribe(AlertType.END_PARKING, cellphoneAssociated);
	}

	public double getMaxHours() {
		return balance / ParkingSystem.PRICE_PER_HOUR;
	}

	public StartParkingResponse startParking() throws InsufficientBalanceException {
		LocalDateTime now = LocalDateTime.now();

		if(this.balance == 0) {
			throw new InsufficientBalanceException("Insufficient balance. Parking not allowed.");
		}

		if(now.getHour() < 8 || now.getHour() > 20) {
			throw new InsufficientBalanceException("It is not possible to generate a parking lot outside the time range 8 - 20");
		}

		LocalDateTime dateInitParking = LocalDateTime.now();
		double estimatedEndTime = dateInitParking.getHour() + this.getMaxHours();

		balance -= this.getMaxHours() * ParkingSystem.PRICE_PER_HOUR;

		currentArea.createParking(carAssociated.getPatent());

		return StartParkingResponse.newBuilder()
				.startHour(LocalDateTime.now())
				.maxHour(estimatedEndTime >= 20 ? 20D : estimatedEndTime)
				.build();
	}

	public void endParking() {
		currentArea.removeParking(carAssociated.getPatent());
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

	@Override
	public void driving() {
		alertManager.notify(AlertType.END_PARKING, "Alert start parking");
	}

	@Override
	public void walking() {
		alertManager.notify(AlertType.START_PARKING, "Alert end parking");
	}
}
