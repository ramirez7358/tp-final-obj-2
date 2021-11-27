package com.unq.app.sem.activities;

import java.time.LocalTime;

public class StartParkingResponse implements Activity {

	private LocalTime startHour;
	private LocalTime maxHour;
	
	public StartParkingResponse(LocalTime startHour, LocalTime maxHour) {
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

	@Override
	public String message() {
		return String.format(
				"Start parking with the following information: Start hour: %s, Max hours: %s",
				this.startHour,
				this.maxHour
		);
	}

	public static class Builder {
		private LocalTime startHour;
		private LocalTime maxHour;

		public Builder startHour(LocalTime startHour) {
			this.startHour = startHour;
			return this;
		}

		public Builder maxHour(LocalTime maxHour) {
			this.maxHour = maxHour;
			return this;
		}

		public StartParkingResponse build() {
			return new StartParkingResponse(this);
		}
	}

}
