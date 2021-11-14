package com.unq;

import com.unq.app.inspector.Inspector;
import com.unq.parking.Parking;

import java.util.List;


public class ParkingArea {

	private List<Parking> parkings;
	private List<PointOfSale> pointOfSales;
	private Inspector inspector;

	public ParkingArea(List<Parking> parkings, List<PointOfSale> pointOfSales, Inspector inspector) {
		
		
	}
	
	public Parking createParking(String phoneNumber, String patent) {
		return null;
	}
	
	public Parking removeParking(String patent) {
		return null;
	}

	public Boolean existParking(String patent) {
		// Devuelve si hay un estacionamiento vigente o no
		return null;
	}

	public List<Parking> getParkings() {
		return parkings;
	}

	public void setParkings(List<Parking> parkings) {
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
}
