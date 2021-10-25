package parking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class ParkingSystem {
	
	private ParkingSystem intstance;
	private LocalDate star_time;
	private LocalDate end_time;
	private LocalDate price_per_hour;
	private Map<String, Integer> credits;
	private List<String> violations;
	
	public ParkingSystem() {
		this.credits = new HashMap<String, Integer>();
		this.violations = new ArrayList<String>();
	
	}
	
	public ParkingSystem getInstance() {
		return this.intstance;
		
	}
	
	public void addViolation(String patent) {
		this.violations.add(patent);
	}
	
	public void finalizeAllCurrentParking() {
		return;
	}
	
	

}
