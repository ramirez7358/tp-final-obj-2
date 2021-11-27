package com.unq.parking;

import com.unq.alert.AlertListener;
import com.unq.alert.AlertManager;
import com.unq.alert.AlertType;
import com.unq.app.inspector.Violation;
import com.unq.commons.TimeUtil;
import com.unq.exceptions.CustomException;

import java.time.LocalTime;
import java.util.*;

public class ParkingSystem {

	private Map<String, Double> balances;
	private List<ParkingArea> areas;
	private List<Violation> violations;
	private TimeUtil timeUtil;
	private LocalTime startTime;
	private LocalTime endTime;
	private double pricePerHour;

	private static ParkingSystem instance;
	private final AlertManager alertManager = new AlertManager(AlertType.START_PARKING, AlertType.END_PARKING);

	private ParkingSystem() {
		this.balances = new HashMap<>();
		this.areas = new ArrayList<>();
		this.violations = new ArrayList<>();
		this.timeUtil = new TimeUtil();
		this.startTime = LocalTime.of(7,0,0);
		this.endTime = LocalTime.of(20,0,0);
		this.pricePerHour = 40;
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
	}

	public void unsubscribeParkingMonitoring(AlertListener alertListener) {
		this.alertManager.unsubscribe(AlertType.START_PARKING, alertListener);
		this.alertManager.unsubscribe(AlertType.END_PARKING, alertListener);
	}

	public void notifyMonitors(AlertType alertType, String data) {
		this.alertManager.notify(alertType, data);
	}

	public void finalizeAllCurrentParking() {
		if(timeUtil.nowTime().isAfter(this.endTime)) {
			this.areas.forEach(a -> {
				a.getParkings()
						.entrySet()
						.stream()
						.filter(x -> x.getValue().inForce())
						.forEach(x -> a.removeParking(x.getKey()));
			});
		}
	}

	public void registryViolation(Violation violation) {
		violations.add(violation);
	}

	public Double getBalance(String phoneNumber) {
		return this.balances.getOrDefault(phoneNumber, 0D);
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

	public Map<String, Double> getBalances() {
		return balances;
	}

	public void setBalances(Map<String, Double> balances) {
		this.balances = balances;
	}

	public List<ParkingArea> getAreas() {
		return areas;
	}

	public void setAreas(List<ParkingArea> areas) {
		this.areas = areas;
	}

	public List<Violation> getViolations() {
		return violations;
	}

	public void setViolations(List<Violation> violations) {
		this.violations = violations;
	}

	public TimeUtil getTimeUtil() {
		return timeUtil;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	public static void setInstance(ParkingSystem instance) {
		ParkingSystem.instance = instance;
	}

	public AlertManager getAlertManager() {
		return alertManager;
	}
}
