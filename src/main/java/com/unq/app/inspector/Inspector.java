package com.unq.app.inspector;

import com.unq.commons.TimeUtil;
import com.unq.parking.ParkingArea;

public class Inspector {

	private AppInspector app;
	private ParkingArea area;
	private TimeUtil timeUtil;

	public Inspector(AppInspector app, ParkingArea area) {
		this.app = app;
		this.area = area;
		this.timeUtil = new TimeUtil();
	}

	public Boolean checkParkingValid(String patent) throws Exception {
		return this.app.checkParkingValid(area, patent);
	}

	public void createViolation(String patent) {
		this.app.registryViolation(area, patent,this);
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

	public TimeUtil getTimeUtil() {
		return timeUtil;
	}

	public void setTimeUtil(TimeUtil timeUtil) {
		this.timeUtil = timeUtil;
	}
}
