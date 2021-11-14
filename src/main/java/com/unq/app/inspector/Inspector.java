package com.unq.app.inspector;

import com.unq.parking.Parking;
import com.unq.parking.ParkingArea;

public class Inspector {

	private AppInspector app;
	private ParkingArea area;

	public Inspector(AppInspector app, ParkingArea area) {
		this.app = app;
		this.area = area;
	}

	public Boolean checkParkingValid(String patent) throws Exception {
		Parking parking = area.getParkingByPatent(patent);

		return parking.inForce();
	}

	public AppInspector getApp() {
		return app;
	}

	public void setApp(AppInspector app) {
		this.app = app;
	}

	public ParkingArea getArea() {
		return area;
	}

	public void setArea(ParkingArea area) {
		this.area = area;
	}
}
