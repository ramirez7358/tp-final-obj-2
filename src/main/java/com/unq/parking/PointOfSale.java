package com.unq.parking;

import com.unq.alert.AlertType;
import com.unq.commons.TimeUtil;
import com.unq.purchase.BalancePurchase;
import com.unq.purchase.HoursPurchase;

import java.time.LocalTime;

public class PointOfSale {

	private ParkingArea area;
	private TimeUtil timeUtil;
	private ParkingSystem parkingSystem;

	public PointOfSale(ParkingArea area) {
		this.area = area;
		this.timeUtil = new TimeUtil();
		this.parkingSystem = ParkingSystem.getInstance();
	}
	
	public void buyCredit(String phoneNumber, Double amount) {
		BalancePurchase purchase = new BalancePurchase(area, timeUtil.nowDateTime(), amount, phoneNumber);

		parkingSystem.increaseBalance(phoneNumber, amount);
		parkingSystem.registryPurchase(purchase);
		parkingSystem.notifyMonitors(
				AlertType.BALANCE_BUY,
				String.format("The number %s made a credit purchase for the value of %s", phoneNumber, amount)
		);
	}

	public void buyHoursOfParking(Integer hours, String patent, String phoneNumber) {
		HoursPurchase purchase = new HoursPurchase(area, timeUtil.nowDateTime(), hours, patent);

		double cost = parkingSystem.getPricePerHour() * hours;

		parkingSystem.reduceBalance(phoneNumber,cost);
		parkingSystem.registryPurchase(purchase);

		LocalTime startTime = timeUtil.nowTime();
		LocalTime endTime = timeUtil.nowTime().plusHours(hours);

		ParkingPerPurchase parking = new ParkingPerPurchase(patent, startTime, endTime, purchase);

		area.createParking(phoneNumber, parking);

		parkingSystem.notifyMonitors(
				AlertType.START_PARKING,
				String.format("A parking lot for purchase has been created with the patent %s.", patent)
		);
	}

	public void setArea(ParkingArea area) {
		this.area = area;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
	}

	public void setParkingSystem(ParkingSystem parkingSystem) {
		this.parkingSystem = parkingSystem;
	}
}
