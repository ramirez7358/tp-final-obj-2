package com.unq.app.inspector;

import com.unq.exceptions.CustomException;
import com.unq.parking.ParkingSystem;
import com.unq.commons.TimeUtil;
import com.unq.parking.Parking;
import com.unq.parking.ParkingArea;

public class AppInspector {

	private Inspector inspector;
	private ParkingSystem parkingSystem;
	private TimeUtil timeUtil;
	
	public AppInspector(Inspector inspector) {
		this.inspector = inspector;
		this.parkingSystem = ParkingSystem.getInstance();
		this.timeUtil = new TimeUtil();
	}

	public Boolean checkParkingValid(String patent) {
		ParkingArea area = inspector.getParkingArea();
		Parking parking = area.getParkingByPatent(patent);

		return parking.inForce();
	}

	public Violation registryViolation(String patent) {
		if(!checkParkingValid(patent)) {
			Violation violation = new Violation(patent, timeUtil.nowDateTime(), inspector, inspector.getParkingArea());
			parkingSystem.registryViolation(violation);
			return violation;
		}else {
			throw new CustomException.InspectionException("The patent has a valid parking.");
		}
	}

	public void setParkingSystem(ParkingSystem parkingSystem) {
		this.parkingSystem = parkingSystem;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
	}
}
