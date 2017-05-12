package vc.report.generator;

import static vc.report.util.SessionManagementUtil.createSessionsInADay;
import static vc.report.util.SessionManagementUtil.getSessionVehicleEntries;

import java.util.Arrays;
import java.util.List;

import vc.common.dto.VehicleEntry;
import vc.common.exception.InValidIntervalException;
import vc.common.model.Session;
import vc.report.customiser.IReportCustomiser;
import vc.system.config.Interval;

public class VehicleEntryReportGenerator {
		
		List<VehicleEntry> vehicleEntries;

		final IReportCustomiser reportCustomiser;
		
		public VehicleEntryReportGenerator(IReportCustomiser reportCustomiser) {
			this.reportCustomiser = reportCustomiser;
		}
		
		public void setVehicleEntries(List<VehicleEntry> vehicleEntries) {
			this.vehicleEntries = vehicleEntries;
		}
		public void printReport(int userInputInterval, boolean storeToFile) throws InValidIntervalException{
			StringBuilder reportToStore = new StringBuilder();
			buildTitle(reportToStore);
			if (userInputInterval == 0){//system default configuration intervals
				List<Interval>intervalAsList = Arrays.asList(Interval.values());
				for (Interval interval : intervalAsList) {
						forEachInterval(reportToStore, interval.getMinutes());
				}
			}else{
				forEachInterval(reportToStore, userInputInterval);
			}
			if (storeToFile){
				// TODO write reportToStore to a file
			}
			System.out.println(reportToStore);
		}
		private void forEachInterval(StringBuilder reportToStore,Integer interval) throws InValidIntervalException {
			List<VehicleEntry> sessionVehicleEntries;
			int peakVolume=0;
			Session peakSession = null;
			List<Session> sessionsInADay = createSessionsInADay(interval);
			
			buildHeader(reportToStore, interval, sessionsInADay);
			for (Session session : sessionsInADay) {
					sessionVehicleEntries = getSessionVehicleEntries(vehicleEntries,session);
					int countForSession = sessionVehicleEntries.size();
					if (countForSession > peakVolume){
						peakVolume = countForSession;
						peakSession = session;
					}
					reportToStore.append(reportCustomiser.getCustomisedReport(sessionVehicleEntries,session));
					reportToStore.append("\n");
			}
			buildFooter(reportToStore, peakVolume, peakSession);
		}

		private void buildFooter(StringBuilder reportToStore, int peakVolume, Session peakSession) {
			reportToStore.append("PEAK VOLUME:"); 
			reportToStore.append(peakVolume);
			reportToStore.append("\t Peak ");
			reportToStore.append(peakSession == null ? "not found" : peakSession.toString());
		}

		private void buildHeader(StringBuilder reportToStore, Integer interval, List<Session> sessionsInADay) {
			reportToStore.append(interval + " minutes Interval");
			reportToStore.append("\n");
			reportToStore.append("No of Sessions/Day: "); 
			reportToStore.append(sessionsInADay.size());
			reportToStore.append("\n\n");
		}

		private void buildTitle(StringBuilder reportToStore) {
			reportToStore.append("\n\n\t\t\t\tVEHICLE SURVEY ANALYSIS REPORT\n\n");
			reportToStore.append("TOTAL TRAFFIC:" + vehicleEntries.size());
			reportToStore.append("\n\n");
		}
}
