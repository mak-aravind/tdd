package vc.report.section;

import java.util.List;

import vc.common.dto.VehicleEntry;
import vc.common.model.Direction;

public class SouthDirectionWiseSubReport extends SubReport{
	MainReport mainReport;
	
	public SouthDirectionWiseSubReport(MainReport mainReport) {
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
		reportToFurnish.append("SOUTH");
		reportToFurnish.append("\t\tTotal:");
		reportToFurnish.append(getNorthEntriesCount());
		reportToFurnish.append("\n");
		return mainReport.getReport() + reportToFurnish.toString();
	}

	private long getNorthEntriesCount() {
		return mainReport.getSessionVehicleEntries()
							  .stream()
							  .filter(entry -> entry.getDirection().equals(Direction.SOUTH))
							  .count();
	}

}
