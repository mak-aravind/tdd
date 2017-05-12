package vc.report.customiser;

import java.util.List;

import vc.common.dto.VehicleEntry;
import vc.common.model.Session;
import vc.report.section.DayWiseSubReport;
import vc.report.section.DistanceSubReport;
import vc.report.section.MainReport;
import vc.report.section.NorthDirectionWiseSubReport;
import vc.report.section.SessionWiseMainReport;
import vc.report.section.SouthDirectionWiseSubReport;
import vc.report.section.SpeedSubReport;

public class DefaultReportCustomiser implements IReportCustomiser{
	public String getCustomisedReport(List<VehicleEntry> sessionVehicleEntries,Session session){
		StringBuilder reportBuilder = new StringBuilder();
		reportBuilder.append(session);
		MainReport sessionDataReportGenerator = new SessionWiseMainReport(sessionVehicleEntries);
		sessionDataReportGenerator = new NorthDirectionWiseSubReport(sessionDataReportGenerator);
		sessionDataReportGenerator = new SouthDirectionWiseSubReport(sessionDataReportGenerator);
		sessionDataReportGenerator = new DayWiseSubReport(sessionDataReportGenerator);
		sessionDataReportGenerator = new DistanceSubReport(sessionDataReportGenerator);
		sessionDataReportGenerator = new SpeedSubReport(sessionDataReportGenerator);
		reportBuilder.append(sessionDataReportGenerator.getReport());		
		return reportBuilder.toString();
	}
}
