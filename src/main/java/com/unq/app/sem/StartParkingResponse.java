package com.unq.app.sem;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StartParkingResponse {
	
	private LocalDateTime startHour;
	private Double maxHour;
	
	public StartParkingResponse(LocalDateTime startHour, Double maxHour) {
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

	public LocalDateTime getStartHour() {
		return startHour;
	}

	public void setStartHour(LocalDateTime startHour) {
		this.startHour = startHour;
	}

	public Double getMaxHour() {
		return maxHour;
	}

	public void setMaxHour(Double maxHour) {
		this.maxHour = maxHour;
	}

	public static class Builder {
		private LocalDateTime startHour;
		private Double maxHour;

		public Builder startHour(LocalDateTime startHour) {
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
