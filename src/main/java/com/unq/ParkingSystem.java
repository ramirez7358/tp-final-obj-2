package com.unq;

import com.unq.app.inspector.Violation;

import java.util.List;

public class ParkingSystem {

	private List<ParkingArea> areas;
	private List<Violation> violations;

	private static ParkingSystem instance;
	public static final int START_TIME = 7;
	public static final int END_TIME = 20;
	public static final double PRICE_PER_HOUR = 40;

	private ParkingSystem() { }

	public static ParkingSystem getInstance() {
		if(instance == null){
			instance = new ParkingSystem();
		}

		return instance;
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
