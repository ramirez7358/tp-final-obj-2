package com.unq.app.sem.activities;

import com.unq.app.sem.Duration;

import java.time.LocalTime;

public class EndParkingResponse implements Activity {
	
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

	@Override
	public String message() {
		return String.format(
				"End parking with the following information: Start hour: %s, End hour: %s, Duration: %s:%s, Cost:%s",
				this.startHour,
				this.endHour,
				this.duration.getHours(),
				this.duration.getMinutes(),
				this.cost
		);
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
