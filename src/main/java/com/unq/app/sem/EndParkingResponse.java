package com.unq.app.sem;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EndParkingResponse {
	
	private LocalDateTime startHour;
	private LocalDateTime endHour;
	private Duration duration;
	private Double cost;
	
	private EndParkingResponse(Builder builder) {
		this.startHour = builder.startHour;
		this.endHour = builder.endHour;
		this.duration = builder.duration;
		this.cost = builder.cost;
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

	public LocalDateTime getEndHour() {
		return endHour;
	}

	public void setEndHour(LocalDateTime endHour) {
		this.endHour = endHour;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public static class Builder {
		private LocalDateTime startHour;
		private LocalDateTime endHour;
		private Duration duration;
		private Double cost;

		public Builder startHour(LocalDateTime startHour) {
			this.startHour = startHour;
			return this;
		}

		public Builder endHour(LocalDateTime endHour) {
			this.endHour = endHour;
			return this;
		}

		public Builder duration(Duration duration) {
			this.duration = duration;
			return this;
		}

		public Builder cost(Double cost) {
			this.cost = cost;
			return this;
		}

		public EndParkingResponse build() {
			return new EndParkingResponse(this);
		}
	}
}
