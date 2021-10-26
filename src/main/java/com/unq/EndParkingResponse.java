package com.unq;

import java.time.LocalDate;

public class EndParkingResponse {
	
	private LocalDate startHour;
	private LocalDate endHour;
	private LocalDate duration;
	private Float cost;
	
	public EndParkingResponse(LocalDate startHour, LocalDate endHour, LocalDate duration, Float cost) {
		this.startHour = startHour;
		this.endHour = endHour;
		this.duration = duration;
		this.cost = cost;
	}
	
	

}
