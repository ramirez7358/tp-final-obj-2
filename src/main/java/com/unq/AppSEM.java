package com.unq;


import com.unq.user.Cellphone;

public class AppSEM {
	private Double balance;
	private Cellphone cellphoneAssociated;
	private ParkingArea currentArea;

	public AppSEM() {
	
	}
	
	public StartParkingResponse startParking(String patent){
		return null;
	}
	
	public EndParkingResponse endParking(Parking parking) {
		return null;
	}

	public void updateCurrentArea(ParkingArea currentArea) {
		this.currentArea = currentArea;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public ParkingArea getCurrentArea() {
		return currentArea;
	}
}
