package com.unq;

import com.unq.parking.Parking;

public class PointOfSale {
	
	public void registryParking(Parking parking) {
		return;
	}
	
	public void buyCredit(String phoneNumber, Double amount) {
		ParkingSystem.getInstance().increaseBalance(phoneNumber, amount);
	}

}
