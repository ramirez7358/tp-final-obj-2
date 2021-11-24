package com.unq.parking;

import com.unq.commons.TimeUtil;
import com.unq.purchase.BalancePurchase;
import com.unq.purchase.HoursPurchase;
import com.unq.purchase.ShoppingRecord;

public class PointOfSale {

	private ParkingArea area;
	private TimeUtil timeUtil;
	private ParkingSystem parkingSystem;
	private ShoppingRecord shoppingRecord;

	public PointOfSale(ParkingArea area) {
		this.area = area;
		this.timeUtil = new TimeUtil();
		this.parkingSystem = ParkingSystem.getInstance();
		this.shoppingRecord = ShoppingRecord.getInstance();
	}
	
	public void buyCredit(String phoneNumber, Double amount) {
		BalancePurchase purchase = new BalancePurchase(area, timeUtil.nowDateTime(), amount, phoneNumber);

		parkingSystem.increaseBalance(phoneNumber, amount);
		shoppingRecord.addPurchase(purchase);
	}

	public void buyHours(Integer hours, String patent, String phoneNumber) {
		HoursPurchase purchase = new HoursPurchase(area, timeUtil.nowDateTime(), hours, patent);

		double cost = parkingSystem.getPricePerHour() * hours;

		parkingSystem.reduceBalance(phoneNumber,cost);
		shoppingRecord.addPurchase(purchase);

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

	public ShoppingRecord getShoppingRecord() {
		return shoppingRecord;
	}

	public void setShoppingRecord(ShoppingRecord shoppingRecord) {
		this.shoppingRecord = shoppingRecord;
	}
}
