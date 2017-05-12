package vc.report.section;

import static vc.common.constants.Speed.AVERAGE_LENGTH_BETWEEN_AXLES_IN_KMS;

import java.util.List;

import vc.common.dto.VehicleEntry;
import vc.common.util.TimeUtil;

public class SpeedSubReport extends SubReport{
	MainReport mainReport;
	
	public SpeedSubReport(MainReport reportGenerator) {
		this.mainReport = reportGenerator;
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
		final double averageSpeed = mainReport.getSessionVehicleEntries()
										.stream()
										.mapToDouble(SpeedSubReport::speedInKMPH)
										.average()
										.orElse(0.0);
		reportToFurnish.append("Average Speed(in KMH):");
		reportToFurnish.append(averageSpeed);
		reportToFurnish.append("\n");
		return mainReport.getReport() + reportToFurnish;
	}

    public static double speedInKMPH(VehicleEntry entry){
    	final double timeTookToCrossSensorsInHours = TimeUtil.convertToHour(entry.timeTookToCrossSensors());
        return  AVERAGE_LENGTH_BETWEEN_AXLES_IN_KMS / timeTookToCrossSensorsInHours;
    }
}
