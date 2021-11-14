package com.unq.parking;

import com.unq.ParkingSystem;
import com.unq.PointOfSale;
import com.unq.TimeUtil;
import com.unq.app.inspector.Inspector;
import com.unq.parking.Parking;

import java.time.temporal.ChronoUnit;
import java.util.*;


public class ParkingArea {

	private Map<String, Parking> parkings;
	private List<PointOfSale> pointOfSales;
	private Inspector inspector;
	private TimeUtil timeUtil;

	public ParkingArea(Inspector inspector) {
		this.parkings = new HashMap<>();
		this.pointOfSales = new ArrayList<>();
		this.inspector = inspector;
		this.timeUtil = new TimeUtil();
	}

	public Parking createParking(String phoneNumber, String patent) {
		Parking parking = new Parking(patent, new ParkingPerAppStrategy(), timeUtil.now());
		parkings.put(phoneNumber, parking);
		return parking;
	}
	
	public Parking removeParking(String phoneNumber) {
		return parkings.remove(phoneNumber);
	}

	public Boolean existParking(String phoneNumber) {
		return parkings.get(phoneNumber) != null;
	}

	public Map<String, Parking> getParkings() {
		return parkings;
	}

	public void setParkings(Map<String, Parking> parkings) {
		this.parkings = parkings;
	}

	public List<PointOfSale> getPointOfSales() {
		return pointOfSales;
	}

	public void setPointOfSales(List<PointOfSale> pointOfSales) {
		this.pointOfSales = pointOfSales;
	}

	public Inspector getInspector() {
		return inspector;
	}

	public void setInspector(Inspector inspector) {
		this.inspector = inspector;
	}

	public TimeUtil getTimeUtil() {
		return timeUtil;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
	}
}
