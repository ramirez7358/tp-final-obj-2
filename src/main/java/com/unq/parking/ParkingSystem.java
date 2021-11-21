package com.unq.parking;

import com.unq.app.inspector.Violation;
import com.unq.commons.TimeUtil;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class ParkingSystem {

	private Map<String, Double> balances;
	private List<ParkingArea> areas;
	private List<Violation> violations;
	private TimeUtil timeUtil;

	private static ParkingSystem instance;
	public static final LocalTime START_TIME = LocalTime.of(7,0,0);
	public static final LocalTime END_TIME = LocalTime.of(20,0,0);
	public static final double PRICE_PER_HOUR = 40;

	private ParkingSystem() {
		this.balances = new HashMap<>();
		this.areas = new ArrayList<>();
		this.violations = new ArrayList<>();
		this.timeUtil = new TimeUtil();
	}

	public static ParkingSystem getInstance() {
		if(instance == null){
			instance = new ParkingSystem();
		}

		return instance;
	}

	public void finalizeAllCurrentParking() {
		if(timeUtil.nowTime().isAfter(END_TIME)) {
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

	public TimeUtil getTimeUtil() {
		return timeUtil;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
	}
}