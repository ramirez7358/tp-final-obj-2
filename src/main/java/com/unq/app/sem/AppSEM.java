package com.unq.app.sem;


import com.unq.ParkingArea;
import com.unq.ParkingSystem;
import com.unq.TimeUtil;
import com.unq.alert.AlertManager;
import com.unq.alert.AlertType;
import com.unq.app.sem.mode.ParkingModeStrategy;
import com.unq.exceptions.InsufficientBalanceException;
import com.unq.parking.Parking;
import com.unq.parking.ParkingStrategy;
import com.unq.user.Car;
import com.unq.user.Cellphone;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class AppSEM implements MovementSensor {
	private Double balance;
	private Cellphone cellphoneAssociated;
	private Car carAssociated;
	private ParkingArea currentArea;
	private AlertManager alertManager;
	private ParkingModeStrategy parkingModeStrategy;
	private TimeUtil timeUtil;

	public AppSEM(Double balance, Cellphone cellphoneAssociated, Car carAssociated, ParkingArea currentArea,
				  ParkingModeStrategy parkingModeStrategy) {
		this.balance = balance;
		this.cellphoneAssociated = cellphoneAssociated;
		this.carAssociated = carAssociated;
		this.currentArea = currentArea;
		this.alertManager = new AlertManager(AlertType.START_PARKING, AlertType.END_PARKING);
		this.parkingModeStrategy = parkingModeStrategy;
		this.timeUtil = new TimeUtil();

		alertManager.subscribe(AlertType.START_PARKING, cellphoneAssociated);
		alertManager.subscribe(AlertType.END_PARKING, cellphoneAssociated);
	}

	public double getMaxHours() {
		return balance / ParkingSystem.PRICE_PER_HOUR;
	}

	public StartParkingResponse startParking() throws InsufficientBalanceException {

		LocalDateTime now = timeUtil.now();

		if(this.balance == 0) {
			throw new InsufficientBalanceException("Insufficient balance. Parking not allowed.");
		}

		if(now.getHour() < 8 || now.getHour() > 20) {
			throw new InsufficientBalanceException("It is not possible to generate a parking lot outside the time range 8 - 20");
		}

		double estimatedEndTime = now.getHour() + this.getMaxHours();

		currentArea.createParking(cellphoneAssociated.getPhoneNumber(), carAssociated.getPatent());

		return StartParkingResponse.newBuilder()
				.startHour(now)
				.maxHour(estimatedEndTime >= 20 ? 20D : estimatedEndTime)
				.build();
	}

	public EndParkingResponse endParking() {
		Parking parking = currentArea.removeParking(cellphoneAssociated.getPhoneNumber());

		long minutes = ChronoUnit.MINUTES.between(parking.getCreationDateTime(), timeUtil.now());

		Duration duration = new Duration(
				minutes / 60,
				minutes % 60
		);

		double cost = minutes * (ParkingSystem.PRICE_PER_HOUR/60);

		balance -= cost;

		return EndParkingResponse.newBuilder()
				.startHour(parking.getCreationDateTime())
				.endHour(timeUtil.now())
				.duration(duration)
				.cost(cost)
				.build();
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

	public void setCurrentArea(ParkingArea currentArea) {
		this.currentArea = currentArea;
	}

	public TimeUtil getTimeUtil() {
		return timeUtil;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
	}

	public ParkingModeStrategy getParkingModeStrategy() {
		return parkingModeStrategy;
	}

	public void setParkingModeStrategy(ParkingModeStrategy parkingModeStrategy) {
		this.parkingModeStrategy = parkingModeStrategy;
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
