package vc.report.section;

import java.util.List;

import vc.common.dto.VehicleEntry;
import vc.common.model.Direction;
public class NorthDirectionWiseSubReport extends SubReport{
	MainReport mainReport;
	
	public NorthDirectionWiseSubReport(MainReport mainReport) {
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
		reportToFurnish.append("NORTH");
		reportToFurnish.append("\t\tTotal:");
		reportToFurnish.append(getEntriesByDirection(Direction.NORTH).size());
		reportToFurnish.append("\n");
		return mainReport.getReport() + reportToFurnish.toString();
	}
}

