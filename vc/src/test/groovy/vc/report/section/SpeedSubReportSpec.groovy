package vc.report.section

import java.awt.Component.BaselineResizeBehavior

import spock.lang.Specification
import vc.transform.classifier.DirectionClassifier
import vc.transform.core.SensorDataTransMutator
import vc.transform.factory.TimeParserFactory

class SpeedSubReportSpec extends Specification{
	
	def sensorDataParser = new TimeParserFactory();
	def directionClassifier = new DirectionClassifier();
	def transMutator = new SensorDataTransMutator(sensorDataParser,directionClassifier)
	
	
	def "Speed Report - with no valid vehicle"(){
		given:
			def inputLines = ["A400000","B402000"]
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseMainReport = new SessionWiseMainReport(vehicleEntries)
			sessionWiseMainReport = new SpeedSubReport(sessionWiseMainReport);
		expect:
			def expectedSessonReportLine1 = "\t\tTotal:" + vehicleEntries.size() +"\n";
			def expectedSpeedInKMPH = 0.0
			def expectedSpeedDistributionReport = "Average Speed(in KMH):" + expectedSpeedInKMPH +"\n"
			def expectedReport = expectedSessonReportLine1 + expectedSpeedDistributionReport
			sessionWiseMainReport.getReport().equals(expectedReport)
			println("<----------------MAK-BUILT-REPORT-------->")
			print(expectedReport)
			println("<----------------MAK-DEBUG-------->")
	}
	
	def "Speed Report - with one vehicle"(){
		given:
			def inputLines = ["A400000","A402000"]
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseMainReport = new SessionWiseMainReport(vehicleEntries)
			sessionWiseMainReport = new SpeedSubReport(sessionWiseMainReport);
		expect:
			def expectedSessonReportLine1 = "\t\tTotal:" + vehicleEntries.size() +"\n";
			def expectedSpeedInKMPH = sessionWiseMainReport.speedInKMPH(vehicleEntries.get(0));
			def expectedSpeedDistributionReport = "Average Speed(in KMH):" + expectedSpeedInKMPH +"\n"
			def expectedReport = expectedSessonReportLine1 + expectedSpeedDistributionReport
			sessionWiseMainReport.getReport().equals(expectedReport)
			println("<----------------MAK-BUILT-REPORT-------->")
			print(expectedReport)
			println("<----------------MAK-DEBUG-------->")
	}
	
	def "Speed Report - with TWO vehicle"(){
		given:
			def inputLines = ["A400000","A402000","A404000","A406000"]
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseMainReport = new SessionWiseMainReport(vehicleEntries)
			sessionWiseMainReport = new SpeedSubReport(sessionWiseMainReport);
		expect:
			def expectedSessonReportLine1 = "\t\tTotal:" + vehicleEntries.size() +"\n";
			def expectedSpeedInKMPHForVehicle1 = sessionWiseMainReport.speedInKMPH(vehicleEntries.get(0));
			def expectedSpeedInKMPHForVehicle2 = sessionWiseMainReport.speedInKMPH(vehicleEntries.get(1));
			def average = (expectedSpeedInKMPHForVehicle1 + expectedSpeedInKMPHForVehicle2) / vehicleEntries.size()
			def expectedSpeedDistributionReport = "Average Speed(in KMH):" + average +"\n"
			def expectedReport = expectedSessonReportLine1 + expectedSpeedDistributionReport
			sessionWiseMainReport.getReport().equals(expectedReport)
			println("<----------------MAK-BUILT-REPORT-------->")
			print(expectedReport)
			println("<----------------MAK-DEBUG-------->")
	}
}
