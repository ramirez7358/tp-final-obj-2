package com.unq.app.inspector;

import com.unq.parking.ParkingArea;

public class Inspector {

	private String name;
	private String lastName;
	private String dni;
	private ParkingArea parkingArea;

	public Inspector(String name, String lastName, String dni, ParkingArea parkingArea) {
		this.name = name;
		this.lastName = lastName;
		this.dni = dni;
		this.parkingArea = parkingArea;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getDni() {
		return dni;
	}

	public ParkingArea getParkingArea() {
		return parkingArea;
	}

	public void setParkingArea(ParkingArea parkingArea) {
		this.parkingArea = parkingArea;
	}
}
