package parking;

import java.time.LocalDate;

public class StartParkingResponse {
	
	private LocalDate startHour;
	private Integer maxHour;
	
	public StartParkingResponse(LocalDate startHour, Integer maxHour) {
		this.startHour = startHour;
		this.maxHour = maxHour;
	}
	
	
	

}
