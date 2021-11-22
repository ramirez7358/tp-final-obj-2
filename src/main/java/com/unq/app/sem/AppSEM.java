package com.unq.app.sem;


import com.unq.alert.AlertListener;
import com.unq.app.sem.mode.ManualModeStrategy;
import com.unq.app.sem.mode.ModeStrategy;
import com.unq.app.sem.movement.MovementState;
import com.unq.parking.ParkingArea;
import com.unq.parking.ParkingSystem;
import com.unq.commons.TimeUtil;
import com.unq.alert.AlertManager;
import com.unq.alert.AlertType;
import com.unq.parking.Parking;
import com.unq.parking.ParkingPerApp;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.unq.exceptions.CustomException.*;

public class AppSEM implements MovementSensor, AlertListener {
	private ParkingArea currentArea;
	private AlertManager alertManager;
	private ModeStrategy appMode;
	private TimeUtil timeUtil;
	private String phoneNumber;
	private String patentCarAssociated;
	private MovementState movementState;

	private final ParkingSystem parkingSystem = ParkingSystem.getInstance();

	public AppSEM(String phoneNumber, String patentCarAssociated, MovementState movementState) {
		this.alertManager = new AlertManager(AlertType.START_PARKING, AlertType.END_PARKING);
		this.appMode = new ManualModeStrategy();
		this.phoneNumber = phoneNumber;
		this.patentCarAssociated = patentCarAssociated;
		this.timeUtil = new TimeUtil();
		this.movementState = movementState;
	}

	public void showMessage(String message) {
		System.out.println(message);
	}

	public double getMaxHours() {
		Double balance = parkingSystem.getBalance(phoneNumber);
		return balance / ParkingSystem.PRICE_PER_HOUR;
	}

	public double getBalance() {
		return parkingSystem.getBalance(phoneNumber);
	}

	public StartParkingResponse startParking() {
		LocalTime now = timeUtil.nowTime();
		Double balance = this.getBalance();

		try {
			this.validateBalance(balance);
			this.validateHour(now);
		} catch (InsufficientBalanceException e) {
			//throw new InsufficientBalanceException(e.getMessage());
		}

		double estimatedEndTime = now.getHour() + this.getMaxHours();

		ParkingPerApp parking = new ParkingPerApp(patentCarAssociated, phoneNumber);
		currentArea.createParking(phoneNumber, parking);

		return StartParkingResponse.newBuilder()
				.startHour(now)
				.maxHour(estimatedEndTime >= 20 ? 20D : estimatedEndTime)
				.build();
	}

	public EndParkingResponse endParking() {
		Parking parking = currentArea.removeParking(phoneNumber);

		long minutes = ChronoUnit.MINUTES.between(parking.getCreationTime(), timeUtil.nowTime());

		Duration duration = new Duration(
				minutes / 60,
				minutes % 60
		);

		double cost = minutes * (ParkingSystem.PRICE_PER_HOUR/60);

		parkingSystem.reduceBalance(phoneNumber, cost);

		return EndParkingResponse.newBuilder()
				.startHour(parking.getCreationTime())
				.endHour(timeUtil.nowTime())
				.duration(duration)
				.cost(cost)
				.build();
	}

	public ParkingArea getCurrentArea() {
		return currentArea;
	}

	public void setCurrentArea(ParkingArea currentArea) {
		this.currentArea = currentArea;
	}

	public AlertManager getAlertManager() {
		return alertManager;
	}

	public void setAlertManager(AlertManager alertManager) {
		this.alertManager = alertManager;
	}

	public ModeStrategy getAppMode() {
		return appMode;
	}

	public void setAppMode(ModeStrategy appMode) {
		this.appMode = appMode;
	}

	public TimeUtil getTimeUtil() {
		return timeUtil;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
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

	public ParkingSystem getParkingSystem() {
		return parkingSystem;
	}

	public MovementState getMovementState() {
		return movementState;
	}

	public void setMovementState(MovementState movementState) {
		this.movementState = movementState;
	}

	private void validateBalance(Double balance) throws InsufficientBalanceException {
		if(balance.equals(0.0)) {
			throw new InsufficientBalanceException("Insufficient balance. Parking not allowed.");
		}
	}

	private void validateHour(LocalTime time) throws InsufficientBalanceException {
		if(time.isBefore(ParkingSystem.START_TIME) || time.isAfter(ParkingSystem.END_TIME)) {
			throw new InsufficientBalanceException("It is not possible to generate a parking lot outside the time range 8 - 20");
		}
	}

	@Override
	public void driving() {
		this.movementState.startDriving(this);
	}

	@Override
	public void walking() {
		this.movementState.startWalking(this);
	}

	@Override
	public void update(AlertType alertType, String data) {

	}
}
