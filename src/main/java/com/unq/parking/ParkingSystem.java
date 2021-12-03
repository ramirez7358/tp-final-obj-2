package com.unq.parking;

import com.unq.alert.AlertListener;
import com.unq.alert.AlertManager;
import com.unq.alert.AlertType;
import com.unq.app.inspector.Violation;
import com.unq.commons.TimeUtil;
import com.unq.exceptions.CustomException;
import com.unq.purchase.Purchase;

import java.time.LocalTime;
import java.util.*;

public class ParkingSystem {

	private Map<String, Double> balances;
	private List<ParkingArea> areas;
	private List<Violation> violations;
	private List<Purchase> shoppingRecord;
	private TimeUtil timeUtil;
	private LocalTime startTime;
	private LocalTime endTime;
	private double pricePerHour;

	private static ParkingSystem instance;
	private AlertManager alertManager ;

	private ParkingSystem() {
		this.balances = new HashMap<>();
		this.areas = new ArrayList<>();
		this.violations = new ArrayList<>();
		this.shoppingRecord = new ArrayList<>();
		this.timeUtil = new TimeUtil();
		this.startTime = LocalTime.of(7,0,0);
		this.endTime = LocalTime.of(20,0,0);
		this.pricePerHour = 40;
		this.alertManager = new AlertManager(AlertType.START_PARKING, AlertType.END_PARKING);
	}

	public static ParkingSystem getInstance() {
		if(instance == null){
			instance = new ParkingSystem();
		}

		return instance;
	}

	public void subscribeParkingMonitoring(AlertListener alertListener) {
		this.alertManager.subscribe(AlertType.START_PARKING, alertListener);
		this.alertManager.subscribe(AlertType.END_PARKING, alertListener);
		this.alertManager.subscribe(AlertType.BALANCE_BUY, alertListener);
	}

	public void unsubscribeParkingMonitoring(AlertListener alertListener) {
		this.alertManager.unsubscribe(AlertType.START_PARKING, alertListener);
		this.alertManager.unsubscribe(AlertType.END_PARKING, alertListener);
		this.alertManager.unsubscribe(AlertType.BALANCE_BUY, alertListener);
	}

	public void notifyMonitors(AlertType alertType, String data) {
		this.alertManager.notify(alertType, data);
	}

	public void registryPurchase(Purchase purchase) {
		this.shoppingRecord.add(purchase);
	}

	public void addParkingArea(ParkingArea area) {
		this.areas.add(area);
	}

	public void finalizeAllCurrentParking() {
		if(timeUtil.nowTime().isAfter(endTime)) {
			this.areas.forEach(a -> a.getParkings()
					.entrySet()
					.stream()
					.filter(x -> x.getValue().inForce())
					.forEach(x -> this.forceEndParking(x.getKey(), a))
			);
		}
	}

	private void forceEndParking(String phoneNumber, ParkingArea area) {
		Parking parking = area.removeParking(phoneNumber);

		long minutes = timeUtil.calculateTimeFrom(parking.getCreationTime());

		double cost = this.getCost(minutes);

		this.reduceBalance(phoneNumber, cost);
	}

	public double getCost(long minutes) {
		return minutes * (pricePerHour/60);
	}

	public void registryViolation(Violation violation) {
		violations.add(violation);
	}

	public Double getBalance(String phoneNumber) {
		return this.balances.getOrDefault(phoneNumber, 0D);
	}

	public void changePricePerHour(Double newPrice) {
		this.pricePerHour = newPrice;
	}

	public void changeStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public void changeEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public void increaseBalance(String phoneNumber, Double amount) {
		if(balances.containsKey(phoneNumber)) {
			Double balance = balances.get(phoneNumber);
			balances.put(phoneNumber, balance + amount);
		}else {
			balances.put(phoneNumber, amount);
		}
	}

	public void reduceBalance(String phoneNumber, Double amount) {
		if(balances.containsKey(phoneNumber)) {
			Double balance = balances.get(phoneNumber);
			balances.put(phoneNumber, balance - amount);
		}else {
			throw new CustomException.UserNotFoundException("The user has no registered credit.");
		}
	}

	public List<Violation> getViolations() {
		return violations;
	}

	public List<Purchase> getShoppingRecord() {
		return shoppingRecord;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setAlertManager(AlertManager alertManager) {
		this.alertManager = alertManager;
	}
}
