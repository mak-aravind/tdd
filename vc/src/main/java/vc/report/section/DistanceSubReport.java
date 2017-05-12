package vc.report.section;

import static vc.common.constants.Speed.CITY_SPEED_LIMIT_IN_KPH;
import static vc.common.constants.Time.MILLISECONDS_IN_A_SECOND;

import java.util.List;

import vc.common.dto.VehicleEntry;
import vc.common.model.Direction;

public class DistanceSubReport extends SubReport{
	MainReport mainReport;
	
	public DistanceSubReport(MainReport mainReport) {
		this.mainReport = mainReport;
	}
	@Override
	public List<VehicleEntry> getSessionVehicleEntries() {
		return mainReport.getSessionVehicleEntries();
	}
	@Override
	public String aggregate() {
		return mainReport.aggregate();
	}

	@Override
	public String getReport() {

		StringBuilder reportToFurnish = new StringBuilder();
		reportToFurnish.append("Rough distance between cars:");
		reportToFurnish.append("\n");
		buildDistanceReportDirectionWise(reportToFurnish,Direction.NORTH);
		reportToFurnish.append("\t\t");
		buildDistanceReportDirectionWise(reportToFurnish,Direction.SOUTH);
		reportToFurnish.append("\n");
		return mainReport.getReport() + reportToFurnish.toString();
	}
	private void buildDistanceReportDirectionWise(StringBuilder reportToFurnish,Direction direction) {
		List<VehicleEntry> entriesByDirection = getEntriesByDirection(direction);
		final double averageTimeGapBetweenVehicles = getAverageTimeGapBetweenVehicles(entriesByDirection);
		final double averageDistanceBetweenVehicles = averageTimeGapBetweenVehicles * getAverageSpeedPerMilliSecond();
		final String directionToFurnish = Direction.NORTH.equals(direction) ? "NORTH" : "SOUTH";
		
		reportToFurnish.append(directionToFurnish +":");
		reportToFurnish.append(averageDistanceBetweenVehicles + " mts");
	}
	
	public double getAverageTimeGapBetweenVehicles(List<VehicleEntry> entriesByDirectionBound) {
		if (entriesByDirectionBound.isEmpty()) return 0;
		final int size = entriesByDirectionBound.size();
                
        final long lastEntryTime = entriesByDirectionBound.get(size - 1)
        											.entryTime()
        											.getTime();
        
        final long firstEntryTime = entriesByDirectionBound.get(0)
        											 .entryTime()
        											 .getTime();
        
        final double averageTimeGapBetweenVehicles = ((double) lastEntryTime - (double) firstEntryTime) / (double)size;
        return  averageTimeGapBetweenVehicles;
	}
	
	public double getAverageSpeedPerMilliSecond(){
		final double CITY_SPEED_LIMIT_IN_METERS_PER_HOUR = (5.0/18.0) * CITY_SPEED_LIMIT_IN_KPH;
		final double AVERAGE_SPEED_PER_MILLI_SECOND = CITY_SPEED_LIMIT_IN_METERS_PER_HOUR/MILLISECONDS_IN_A_SECOND;
		return AVERAGE_SPEED_PER_MILLI_SECOND;
	}
}
