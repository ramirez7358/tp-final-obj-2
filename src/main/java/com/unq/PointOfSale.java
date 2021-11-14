package com.unq;

import com.unq.parking.ParkingArea;
import com.unq.parking.ParkingPerPurchaseStrategy;
import com.unq.purchase.BalancePurchase;
import com.unq.purchase.HoursPurchase;
import com.unq.purchase.ShoppingRecord;

public class PointOfSale {

	private ParkingArea area;
	private TimeUtil timeUtil;

	public PointOfSale(ParkingArea area) {
		this.area = area;
		this.timeUtil = new TimeUtil();
	}
	
	public void buyCredit(String phoneNumber, Double amount) {
		BalancePurchase purchase = new BalancePurchase(area, timeUtil.nowDateTime(), amount, phoneNumber);

		ParkingSystem.getInstance().increaseBalance(phoneNumber, amount);
		ShoppingRecord.getInstance().addPurchase(purchase);
	}

	public void buyHours(Integer hours, String patent, String phoneNumber) {
		HoursPurchase purchase = new HoursPurchase(area, timeUtil.nowDateTime(), hours, patent);

		double cost = ParkingSystem.PRICE_PER_HOUR * hours;

		ParkingSystem.getInstance().reduceBalance(phoneNumber,cost);
		ShoppingRecord.getInstance().addPurchase(purchase);

		area.createParking(phoneNumber, patent, new ParkingPerPurchaseStrategy());
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
}
