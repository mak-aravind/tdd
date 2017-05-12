package vc.report.section;

import java.util.List;

import vc.common.dto.VehicleEntry;

public class SessionWiseMainReport extends MainReport{
	public SessionWiseMainReport(List<VehicleEntry> sessionVehicleEntries) {
		super.sessionVehicleEntries = sessionVehicleEntries;
		linesToDisplay = getLinesToDisplay();
	}
	
	public String getReport(){
		return linesToDisplay;
		
	}

	private String getLinesToDisplay() {
		StringBuilder reportToFurnish = new StringBuilder();
		reportToFurnish.append("\t\tTotal:");
		reportToFurnish.append(sessionVehicleEntries.size());
		reportToFurnish.append("\n");
		return reportToFurnish.toString();
	}

	@Override
	public String aggregate() {
		return "SessionWiseMainReport";
	}
}
