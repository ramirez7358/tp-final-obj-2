package com.unq.app.inspector;

import com.unq.ParkingSystem;
import com.unq.TimeUtil;
import com.unq.parking.Parking;
import com.unq.parking.ParkingArea;

public class AppInspector {

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
		ParkingSystem.getInstance().registryViolation(violation);
	}

	public TimeUtil getTimeUtil() {
		return timeUtil;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
	}
}
