package vc.report.section;

import java.util.Arrays;
import java.util.List;

import vc.common.dto.VehicleEntry;
import vc.system.config.Day;

public class DayWiseSubReport extends SubReport{
	MainReport mainReport;
	
	public DayWiseSubReport(MainReport mainReport) {
		this.mainReport = mainReport;
	}
	
	@Override
	public String aggregate() {
		return mainReport.aggregate();
	}
	@Override
	public List<VehicleEntry> getSessionVehicleEntries() {
		return mainReport.getSessionVehicleEntries();
	}
	@Override
	public String getReport() {
		return mainReport.getReport()
				+ getColumnHeadings()
				+ getLinesToDisplay();
	}
	
	private String getLinesToDisplay(){
		Day[] allDaysSupported = Day.values();
		Long[] counts = new Long[allDaysSupported.length];
		for (int i = 0; i < allDaysSupported.length; i++) {
			counts[i] = getSessionsEntriesForDay(i);
		}
		return getformattedString(Arrays.asList(counts));
	}

	private long getSessionsEntriesForDay(int i) {
		return mainReport.getSessionVehicleEntries()
				.parallelStream()
				.filter(entry -> entry.getDay() == i).count();
	}
	
	public String getformattedString(List<Long> counts) {
		String formattedString;
		formattedString = String.format("%-24d%-24d%-24d%-24d%-24d\n", 
				counts.get(0),counts.get(1),counts.get(2),counts.get(3),counts.get(4));
		return formattedString;
		
	}
	public String getColumnHeadings() {
		StringBuilder lineToReturn = new StringBuilder();
		Day[] allDaysSupported = Day.values();
		for (int i = 0; i < allDaysSupported.length; i++) {
			lineToReturn.append(String.format("%-24s", "Day-"+(i+1)));
		}
		lineToReturn.append("\n");
		return lineToReturn.toString();
	}
}
