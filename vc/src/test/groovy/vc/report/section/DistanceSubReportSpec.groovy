package vc.report.section

import static vc.common.constants.Time.MILLISECONDS_IN_A_SECOND

import spock.lang.Specification
import vc.transform.classifier.DirectionClassifier
import vc.transform.core.SensorDataTransMutator
import vc.transform.factory.TimeParserFactory

class DistanceSubReportSpec extends Specification{
	
	def sensorDataParser = new TimeParserFactory();
	def directionClassifier = new DirectionClassifier();
	def transMutator = new SensorDataTransMutator(sensorDataParser,directionClassifier)
	
	def "Distance Report - for NORTH direction"(){
		given:
			def inputLines = ["A400000","A402000","A404000","A406000"]
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseMainReport = new SessionWiseMainReport(vehicleEntries)
			sessionWiseMainReport = new DistanceSubReport(sessionWiseMainReport);
			def averageTimeGapBetweenVehicles = sessionWiseMainReport.getAverageTimeGapBetweenVehicles(vehicleEntries);
		expect:
			def expectedSessonReportLine1 = "\t\tTotal:" + vehicleEntries.size() +"\n";
			def expectedDistanceReportDescription = "Rough distance between cars:\n"
			def expectedDistanceBetweenVehicles =   averageTimeGapBetweenVehicles * sessionWiseMainReport.getAverageSpeedPerMilliSecond();
			def expectedDistanceReportForNorth =  "NORTH:" + expectedDistanceBetweenVehicles + " mts"
			def expectedDistanceForSouth =  "\t\tSOUTH:" + "0.0 mts" 
			def expectedReport = expectedSessonReportLine1 +expectedDistanceReportDescription+expectedDistanceReportForNorth+expectedDistanceForSouth+"\n"
			sessionWiseMainReport.getReport().equals(expectedReport)
			println("<----------------MAK-DEBUG-------->")
			print(expectedReport)
			println("\n<----------------MAK-DEBUG-------->")
	}
	
	def "Distance Report - for SOUTH direction"(){
		given:
			def inputLines = ["A400000","B402000","A404000","B406000","A408000","B410000","A412000","B414000"]
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseMainReport = new SessionWiseMainReport(vehicleEntries)
			sessionWiseMainReport = new DistanceSubReport(sessionWiseMainReport);
			def averageTimeGapBetweenVehicles = sessionWiseMainReport.getAverageTimeGapBetweenVehicles(vehicleEntries);
		expect:
			def expectedSessonReportLine1 = "\t\tTotal:" + vehicleEntries.size() +"\n";
			def expectedDistanceReportDescription = "Rough distance between cars:\n"
			def expectedDistanceBetweenVehicles = averageTimeGapBetweenVehicles * sessionWiseMainReport.getAverageSpeedPerMilliSecond();
			def expectedDistanceReportForNorth =  "NORTH:" + "0.0 mts"
			def expectedDistanceForSouth =  "\t\tSOUTH:" + expectedDistanceBetweenVehicles + " mts"
			def expectedReport = expectedSessonReportLine1 +expectedDistanceReportDescription+expectedDistanceReportForNorth+expectedDistanceForSouth+"\n"
			sessionWiseMainReport.getReport().equals(expectedReport)
			println("<----------------MAK-DEBUG-------->")
			print(expectedReport)
			println("\n<----------------MAK-DEBUG-------->")
	}
}
