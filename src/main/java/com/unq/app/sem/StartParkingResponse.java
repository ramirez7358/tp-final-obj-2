package com.unq.app.sem;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class StartParkingResponse {
	
	private LocalTime startHour;
	private Double maxHour;
	
	public StartParkingResponse(LocalTime startHour, Double maxHour) {
		this.startHour = startHour;
		this.maxHour = maxHour;
	}

	public StartParkingResponse(Builder builder) {
		this.startHour = builder.startHour;
		this.maxHour = builder.maxHour;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public LocalTime getStartHour() {
		return startHour;
	}

	public void setStartHour(LocalTime startHour) {
		this.startHour = startHour;
	}

	public Double getMaxHour() {
		return maxHour;
	}

	public void setMaxHour(Double maxHour) {
		this.maxHour = maxHour;
	}

	public static class Builder {
		private LocalTime startHour;
		private Double maxHour;

		public Builder startHour(LocalTime startHour) {
			this.startHour = startHour;
			return this;
		}

		public Builder maxHour(Double maxHour) {
			this.maxHour = maxHour;
			return this;
		}

		public StartParkingResponse build() {
			return new StartParkingResponse(this);
		}
	}

}
