package com.unq.parking;

import com.unq.app.inspector.Violation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingSystem {

	private Map<String, Double> balances;
	private List<ParkingArea> areas;
	private List<Violation> violations;

	private static ParkingSystem instance;
	public static final LocalTime START_TIME = LocalTime.of(7,0,0);
	public static final LocalTime END_TIME = LocalTime.of(20,0,0);
	public static final double PRICE_PER_HOUR = 40;

	private ParkingSystem() {
		this.balances = new HashMap<>();
		this.areas = new ArrayList<>();
		this.violations = new ArrayList<>();
	}

	public static ParkingSystem getInstance() {
		if(instance == null){
			instance = new ParkingSystem();
		}

		return instance;
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
			System.out.println("The user has no registered credit.");
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

	public static void setInstance(ParkingSystem instance) {
		ParkingSystem.instance = instance;
	}
}
