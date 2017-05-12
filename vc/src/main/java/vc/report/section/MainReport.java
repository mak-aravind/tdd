package vc.report.section;

import java.util.List;
import java.util.stream.Collectors;

import vc.common.dto.VehicleEntry;
import vc.common.model.Direction;

public abstract class MainReport {
	String linesToDisplay;
	List<VehicleEntry> sessionVehicleEntries;

	public List<VehicleEntry> getSessionVehicleEntries(){
		return this.sessionVehicleEntries;
	}
	public abstract String aggregate();
	public abstract String getReport();
	public List<VehicleEntry> getEntriesByDirection(Direction direction) {
		return getSessionVehicleEntries()
							  .stream()
							  //.parallel()
							  .filter(entry -> entry.getDirection() == direction)
							  .collect(Collectors.toList());
	}
}	

