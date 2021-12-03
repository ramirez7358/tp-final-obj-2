package com.unq.parking;

import com.unq.app.inspector.Inspector;
import com.unq.exceptions.CustomException;

import java.util.*;


public class ParkingArea {

	private Map<String, Parking> parkings;
	private List<PointOfSale> pointOfSales;
	private Inspector inspector;

	public ParkingArea(Inspector inspector) {
		this.parkings = new HashMap<>();
		this.pointOfSales = new ArrayList<>();
		this.inspector = inspector;
	}

	public void addPointOfSale(PointOfSale pointOfSale) {
		this.pointOfSales.add(pointOfSale);
	}

	public Boolean containPoint(PointOfSale pointOfSale) {
		return this.pointOfSales.contains(pointOfSale);
	}

	public List<Parking> getAllParkings() {
		return new ArrayList<>(parkings.values());
	}

	public String getPhoneNumberByPatent(String patent) {
		Map.Entry<String, Parking> map = parkings.entrySet().stream()
				.filter(x -> x.getValue().getCarPatent().equals(patent))
				.findAny()
				.orElseThrow(() -> new CustomException.ParkingNotFound("No parking associated with the patent was found"));

		return map.getKey();
	}

	public void createParking(String phoneNumber, Parking parking) {
		parkings.put(phoneNumber, parking);
	}
	
	public Parking removeParking(String phoneNumber) {
		return parkings.remove(phoneNumber);
	}

	public Parking getParkingByPatent(String patent) {
		return parkings.values()
				.stream()
				.filter(p -> p.getCarPatent().equals(patent))
				.findAny()
				.orElseThrow(() -> new CustomException.ParkingNotFound(String.format("No parking associated with the patent was found: %s", patent)));
	}

	public Boolean existParking(String phoneNumber) {
		return parkings.entrySet().stream().anyMatch(p -> p.getKey().equals(phoneNumber));
	}

	public Map<String, Parking> getParkings() {
		return parkings;
	}
}
