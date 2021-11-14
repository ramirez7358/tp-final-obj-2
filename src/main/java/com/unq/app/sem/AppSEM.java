package com.unq.app.sem;


import com.unq.parking.ParkingArea;
import com.unq.ParkingSystem;
import com.unq.TimeUtil;
import com.unq.alert.AlertManager;
import com.unq.alert.AlertType;
import com.unq.exceptions.InsufficientBalanceException;
import com.unq.parking.Parking;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class AppSEM implements MovementSensor {
	private ParkingArea currentArea;
	private AlertManager alertManager;
	private ParkingMode parkingMode;
	private TimeUtil timeUtil;
	private String phoneNumber;
	private String patentCarAssociated;

	public AppSEM(ParkingMode parkingMode, String phoneNumber, String patentCarAssociated) {
		this.alertManager = new AlertManager(AlertType.START_PARKING, AlertType.END_PARKING);
		this.parkingMode = parkingMode;
		this.phoneNumber = phoneNumber;
		this.patentCarAssociated = patentCarAssociated;
		this.timeUtil = new TimeUtil();
	}

	public double getMaxHours(String phoneNumber) {
		Double balance = ParkingSystem.getInstance().getBalance(phoneNumber);
		return balance / ParkingSystem.PRICE_PER_HOUR;
	}

	public StartParkingResponse startParking() {
		LocalTime now = timeUtil.now();
		Double balance = ParkingSystem.getInstance().getBalance(phoneNumber);

		try {
			this.validateBalance(balance);
			this.validateHour(now);
		} catch (InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}

		double estimatedEndTime = now.getHour() + this.getMaxHours(phoneNumber);

		currentArea.createParking(patentCarAssociated, phoneNumber);

		return StartParkingResponse.newBuilder()
				.startHour(now)
				.maxHour(estimatedEndTime >= 20 ? 20D : estimatedEndTime)
				.build();
	}

	public EndParkingResponse endParking() {
		Parking parking = currentArea.removeParking(phoneNumber);

		long minutes = ChronoUnit.MINUTES.between(parking.getCreationTime(), timeUtil.now());

		Duration duration = new Duration(
				minutes / 60,
				minutes % 60
		);

		double cost = minutes * (ParkingSystem.PRICE_PER_HOUR/60);

		ParkingSystem.getInstance().reduceBalance(phoneNumber, cost);

		return EndParkingResponse.newBuilder()
				.startHour(parking.getCreationTime())
				.endHour(timeUtil.now())
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

	public ParkingMode getParkingMode() {
		return parkingMode;
	}

	public void setParkingMode(ParkingMode parkingMode) {
		this.parkingMode = parkingMode;
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
		if(currentArea.existParking(patentCarAssociated) && this.parkingMode == ParkingMode.AUTOMATIC) {
			this.endParking();
			alertManager.notify(AlertType.END_PARKING, "The end of parking has been triggered automatically.");
		}
	}

	@Override
	public void walking() {
		if(this.parkingMode == ParkingMode.AUTOMATIC && !currentArea.existParking(patentCarAssociated)) {
			this.startParking();
			alertManager.notify(AlertType.START_PARKING, "The parking start has been triggered automatically.");
		}
	}
}
