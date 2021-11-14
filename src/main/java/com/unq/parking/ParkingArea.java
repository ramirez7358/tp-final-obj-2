package com.unq.parking;

import com.unq.PointOfSale;
import com.unq.TimeUtil;
import com.unq.app.inspector.Inspector;

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

	public Parking createParking(String phoneNumber, String patent, ParkingStrategy parkingStrategy) {
		Parking parking = new Parking(patent, parkingStrategy, timeUtil.nowTime());
		parkings.put(phoneNumber, parking);
		return parking;
	}
	
	public Parking removeParking(String phoneNumber) {
		return parkings.remove(phoneNumber);
	}

	public Parking getParkingByPatent(String patent) throws Exception {
		Optional<Parking> parking = parkings.values().stream().filter(p -> p.getCarPatent().equals(patent)).findAny();

		if(parking.isPresent()) {
			return parking.get();
		}else {
			throw new Exception(String.format("No parking associated with the patent was found: %s", patent));
		}
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
