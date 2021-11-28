package com.unq.parking;

import com.unq.commons.TimeUtil;
import com.unq.purchase.BalancePurchase;
import com.unq.purchase.HoursPurchase;

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
	}

	public void buyHours(Integer hours, String patent, String phoneNumber) {
		HoursPurchase purchase = new HoursPurchase(area, timeUtil.nowDateTime(), hours, patent);

		double cost = parkingSystem.getPricePerHour() * hours;

		parkingSystem.reduceBalance(phoneNumber,cost);
		parkingSystem.registryPurchase(purchase);

		ParkingPerPurchase parking = new ParkingPerPurchase(patent, timeUtil.nowTime().plusHours(hours), purchase);

		area.createParking(phoneNumber, parking);
	}

	public ParkingArea getArea() {
		return area;
	}

	public void setArea(ParkingArea area) {
		this.area = area;
	}

	public TimeUtil getTimeUtil() {
		return timeUtil;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
	}

	public ParkingSystem getParkingSystem() {
		return parkingSystem;
	}

	public void setParkingSystem(ParkingSystem parkingSystem) {
		this.parkingSystem = parkingSystem;
	}
}
