package vc.report.customiser;

import java.util.List;

import vc.common.dto.VehicleEntry;
import vc.common.model.Session;

public interface IReportCustomiser {
	public String getCustomisedReport(List<VehicleEntry> sessionVehicleEntries,Session session);
}
