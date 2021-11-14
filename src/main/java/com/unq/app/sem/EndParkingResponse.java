package com.unq.app.sem;

import java.time.LocalTime;

public class EndParkingResponse {
	
	private LocalTime startHour;
	private LocalTime endHour;
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

	public LocalTime getStartHour() {
		return startHour;
	}

	public void setStartHour(LocalTime startHour) {
		this.startHour = startHour;
	}

	public LocalTime getEndHour() {
		return endHour;
	}

	public void setEndHour(LocalTime endHour) {
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
		private LocalTime startHour;
		private LocalTime endHour;
		private Duration duration;
		private Double cost;

		public Builder startHour(LocalTime startHour) {
			this.startHour = startHour;
			return this;
		}

		public Builder endHour(LocalTime endHour) {
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
