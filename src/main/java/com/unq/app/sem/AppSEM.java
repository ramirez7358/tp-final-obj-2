package com.unq.app.sem;


import com.unq.app.sem.mode.ManualModeStrategy;
import com.unq.app.sem.mode.ModeStrategy;
import com.unq.parking.ParkingArea;
import com.unq.parking.ParkingSystem;
import com.unq.commons.TimeUtil;
import com.unq.alert.AlertManager;
import com.unq.alert.AlertType;
import com.unq.exceptions.InsufficientBalanceException;
import com.unq.parking.Parking;
import com.unq.parking.ParkingPerApp;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class AppSEM implements MovementSensor {
	private ParkingArea currentArea;
	private AlertManager alertManager;
	private ModeStrategy appMode;
	private TimeUtil timeUtil;
	private String phoneNumber;
	private String patentCarAssociated;

	public AppSEM(String phoneNumber, String patentCarAssociated) {
		this.alertManager = new AlertManager(AlertType.START_PARKING, AlertType.END_PARKING);
		this.appMode = new ManualModeStrategy();
		this.phoneNumber = phoneNumber;
		this.patentCarAssociated = patentCarAssociated;
		this.timeUtil = new TimeUtil();
	}

	public AppSEM(ParkingMode manual) {
	}

	public double getMaxHours(String phoneNumber) {
		Double balance = ParkingSystem.getInstance().getBalance(phoneNumber);
		return balance / ParkingSystem.PRICE_PER_HOUR;
	}

	public StartParkingResponse startParking() {
		LocalTime now = timeUtil.nowTime();
		Double balance = ParkingSystem.getInstance().getBalance(phoneNumber);

		try {
			this.validateBalance(balance);
			this.validateHour(now);
		} catch (InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}

		double estimatedEndTime = now.getHour() + this.getMaxHours(phoneNumber);

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

		ParkingSystem.getInstance().reduceBalance(phoneNumber, cost);

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

	private void validateBalance(Double balance) throws InsufficientBalanceException {
		if(balance == 0) {
			throw new InsufficientBalanceException("Insufficient balance. Parking not allowed.");
		}
	}

	private void validateHour(LocalTime time) throws InsufficientBalanceException {
		if(time.getHour() < 8 || time.getHour() > 20) {
			throw new InsufficientBalanceException("It is not possible to generate a parking lot outside the time range 8 - 20");
		}
	}

	@Override
	public void driving() {
		this.appMode.manageEndParking(this);
	}

	@Override
	public void walking() {
		this.appMode.manageStartParking(this);
	}
}
