package com.unq.app.inspector;

import com.unq.parking.ParkingSystem;
import com.unq.commons.TimeUtil;
import com.unq.parking.Parking;
import com.unq.parking.ParkingArea;

public class AppInspector {

	private final ParkingSystem parkingSystem = ParkingSystem.getInstance();
	private TimeUtil timeUtil;
	
	public AppInspector() {
		this.timeUtil = new TimeUtil();
	}

	public Boolean checkParkingValid(ParkingArea area, String patent) throws Exception {
		Parking parking = area.getParkingByPatent(patent);

		return parking.inForce();
	}

	public void registryViolation(ParkingArea area, String patent, Inspector inspector) {
		Violation violation = new Violation(patent, timeUtil.nowDateTime(), inspector, area);
		parkingSystem.registryViolation(violation);
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
}
