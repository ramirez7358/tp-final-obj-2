package com.unq.app.sem;


import com.unq.alert.AlertType;
import com.unq.app.sem.activities.Activity;
import com.unq.app.sem.activities.EndParkingResponse;
import com.unq.app.sem.activities.StartParkingResponse;
import com.unq.app.sem.mode.ManualModeStrategy;
import com.unq.app.sem.mode.ModeStrategy;
import com.unq.app.sem.movement.MovementState;
import com.unq.parking.ParkingArea;
import com.unq.parking.ParkingSystem;
import com.unq.commons.TimeUtil;
import com.unq.parking.Parking;
import com.unq.parking.ParkingPerApp;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.unq.exceptions.CustomException.*;

public class AppSEM implements MovementSensor {
	private ParkingArea currentArea;
	private ModeStrategy appMode;
	private TimeUtil timeUtil;
	private String phoneNumber;
	private String patentCarAssociated;
	private MovementState movementState;
	private ParkingSystem parkingSystem;
	private List<Activity> activityHistory;

	public AppSEM(String phoneNumber, String patentCarAssociated, MovementState movementState) {
		this.appMode = new ManualModeStrategy();
		this.phoneNumber = phoneNumber;
		this.patentCarAssociated = patentCarAssociated;
		this.timeUtil = new TimeUtil();
		this.movementState = movementState;
		this.parkingSystem = ParkingSystem.getInstance();
		this.activityHistory = new ArrayList<>();
	}

	public double getMaxHours() {
		Double balance = parkingSystem.getBalance(phoneNumber);
		return balance / parkingSystem.getPricePerHour();
	}

	public double getBalance() {
		return parkingSystem.getBalance(phoneNumber);
	}

	public void manageStartParking() {
		this.appMode.manageStartParking(this);
	}

	public void manageEndParking() {
		this.appMode.manageEndParking(this);
	}

	// Metodo para notificar al usuario
	public void sendNotification(String data) {
		System.out.println(data);
	}

	public void startParking() {
		LocalTime now = timeUtil.nowTime();
		Double balance = this.getBalance();

		this.validateBalance(balance);
		this.validateHour(now);

		LocalTime estimatedEndTime = now.plusHours((long) this.getMaxHours());

		ParkingPerApp parking = new ParkingPerApp(patentCarAssociated, now, phoneNumber);
		currentArea.createParking(phoneNumber, parking);

		StartParkingResponse activity = StartParkingResponse.newBuilder()
				.startHour(now)
				.maxHour(estimatedEndTime.isAfter(parkingSystem.getEndTime()) ? parkingSystem.getEndTime() : estimatedEndTime)
				.build();

		this.activityHistory.add(activity);

		parkingSystem.notifyMonitors(
				AlertType.START_PARKING,
				String.format("A parking lot per app has been created with the patent %s.", patentCarAssociated)
		);
	}

	public void endParking() {
		Parking parking = currentArea.removeParking(phoneNumber);

		long minutes = ChronoUnit.MINUTES.between(parking.getCreationTime(), timeUtil.nowTime());

		Duration duration = new Duration(
				minutes / 60,
				minutes % 60
		);

		double cost = minutes * (parkingSystem.getPricePerHour()/60);

		parkingSystem.reduceBalance(phoneNumber, cost);

		EndParkingResponse activity = EndParkingResponse.newBuilder()
				.startHour(parking.getCreationTime())
				.endHour(timeUtil.nowTime())
				.duration(duration)
				.cost(cost)
				.build();

		this.activityHistory.add(activity);

		parkingSystem.notifyMonitors(
				AlertType.END_PARKING,
				String.format("One parking lot per app with the patent %s has been finalized.", patentCarAssociated)
		);
	}

	public void changeArea(ParkingArea currentArea) {
		this.currentArea = currentArea;
	}

	public ModeStrategy getAppMode() {
		return appMode;
	}

	public void setAppMode(ModeStrategy appMode) {
		this.appMode = appMode;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
	}

	public void setParkingSystem(ParkingSystem parkingSystem) {
		this.parkingSystem = parkingSystem;
	}

	public MovementState getMovementState() {
		return this.movementState;
	}

	public void setMovementState(MovementState movementState) {
		this.movementState = movementState;
	}

	public List<Activity> getActivityHistory() {
		return activityHistory;
	}

	private void validateBalance(Double balance) throws InsufficientBalanceException {
		if(balance.equals(0.0)) {
			throw new InsufficientBalanceException("Insufficient balance. Parking not allowed.");
		}
	}

	private void validateHour(LocalTime time) throws InsufficientBalanceException {
		if(time.isBefore(parkingSystem.getStartTime()) || time.isAfter(parkingSystem.getEndTime())) {
			throw new HourOutOfRangeException(
					String.format(
							"It is not possible to generate a parking lot outside the time range %s - %s",
							parkingSystem.getStartTime(),
							parkingSystem.getEndTime()
					)
			);
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
}
