package com.unq;

import java.util.List;

public class ParkingSystem {

	private List<ParkingArea> areas;
	private List<Violation> violations;

	private static ParkingSystem instance;
	private static final int START_TIME = 7;
	private static final int END_TIME = 20;
	private static final float PRICE_PER_HOUR = 40L;

	private ParkingSystem() { }

	private static ParkingSystem getInstance() {
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
