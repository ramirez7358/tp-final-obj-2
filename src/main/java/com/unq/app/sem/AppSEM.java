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
	private ParkingArea currentArea;
	private AlertManager alertManager;
	private ParkingModeStrategy parkingModeStrategy;
	private TimeUtil timeUtil;

	public AppSEM(ParkingModeStrategy parkingModeStrategy) {
		this.alertManager = new AlertManager(AlertType.START_PARKING, AlertType.END_PARKING);
		this.parkingModeStrategy = parkingModeStrategy;
		this.timeUtil = new TimeUtil();
	}

	public double getMaxHours() {
		int balance = 2; //hardcode
		return balance / ParkingSystem.PRICE_PER_HOUR;
	}

	public StartParkingResponse startParking(String patent, String phoneNumber) throws InsufficientBalanceException {
		LocalDateTime now = timeUtil.now();
		Double balance = ParkingSystem.getInstance().getBalance(phoneNumber); // hardcode

		if(balance == 0) {
			throw new InsufficientBalanceException("Insufficient balance. Parking not allowed.");
		}

		if(now.getHour() < 8 || now.getHour() > 20) {
			throw new InsufficientBalanceException("It is not possible to generate a parking lot outside the time range 8 - 20");
		}

		double estimatedEndTime = now.getHour() + this.getMaxHours();

		currentArea.createParking(patent, phoneNumber);

		return StartParkingResponse.newBuilder()
				.startHour(now)
				.maxHour(estimatedEndTime >= 20 ? 20D : estimatedEndTime)
				.build();
	}

	public EndParkingResponse endParking(String phoneNumber) {
		// Al remover el parking hay que descontar credito
		Parking parking = currentArea.removeParking(phoneNumber);

		long minutes = ChronoUnit.MINUTES.between(parking.getCreationDateTime(), timeUtil.now());

		Duration duration = new Duration(
				minutes / 60,
				minutes % 60
		);

		double cost = minutes * (ParkingSystem.PRICE_PER_HOUR/60);

		return EndParkingResponse.newBuilder()
				.startHour(parking.getCreationDateTime())
				.endHour(timeUtil.now())
				.duration(duration)
				.cost(cost)
				.build();
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
