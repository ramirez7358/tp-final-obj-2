package com.unq.parking;

import com.unq.alert.AlertManager;
import com.unq.alert.AlertType;
import com.unq.commons.TimeUtil;
import com.unq.app.inspector.Inspector;

import java.util.*;


public class ParkingArea {

	private Map<String, Parking> parkings;
	private List<PointOfSale> pointOfSales;
	private Inspector inspector;
	private TimeUtil timeUtil;
	private AlertManager alertManager;

	public ParkingArea(Inspector inspector, AlertManager alertManager) {
		this.parkings = new HashMap<>();
		this.pointOfSales = new ArrayList<>();
		this.inspector = inspector;
		this.alertManager = alertManager;
		this.timeUtil = new TimeUtil();
	}

	public void createParking(String phoneNumber, Parking parking) {
		parkings.put(phoneNumber, parking);
		alertManager.notify(
				AlertType.START_PARKING,
				String.format("A car with the patent %s and number %s has started a parking lot.", parking.getCarPatent(), phoneNumber)
		);
	}
	
	public Parking removeParking(String phoneNumber) {
		Parking parking = parkings.remove(phoneNumber);
		alertManager.notify(
				AlertType.END_PARKING,
				String.format("A car with the patent %s and number %s has finished a parking lot.", parking.getCarPatent(), phoneNumber)
		);
		return parking;
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
		return parkings.entrySet().stream().anyMatch(p -> p.getKey().equals(phoneNumber));
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
