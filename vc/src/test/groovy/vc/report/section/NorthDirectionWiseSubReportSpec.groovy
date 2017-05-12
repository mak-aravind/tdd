package vc.report.section


import spock.lang.Specification
import vc.transform.classifier.DirectionClassifier
import vc.transform.core.SensorDataTransMutator
import vc.transform.factory.TimeParserFactory

class NorthDirectionWiseSubReportSpec extends Specification{

	def sensorDataParser = new TimeParserFactory();
	def directionClassifier = new DirectionClassifier();
	def transMutator = new SensorDataTransMutator(sensorDataParser,directionClassifier)
	
	def "File with 8 readings, with sequence A->A->A->A->A->A->A->A yield 4 North Vehicles"(int countForSession,int countNorthBoundVehicles){
		given:
			def inputLines = ["A638379","A638382","A638520","A638523","A1016488","A1016648","A1016588","A1016748"]
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseMainReport = new SessionWiseMainReport(vehicleEntries)
			sessionWiseMainReport = new NorthDirectionWiseSubReport(sessionWiseMainReport)
			def sessionReport = "\t\tTotal:" + countForSession +"\n";
			def directionReport = "NORTH" + "\t\tTotal:" + countNorthBoundVehicles + "\n";
			def expectedReport = sessionReport + directionReport
		expect:
			println("<----------------MAK-DEBUG-------->")
			print(expectedReport)
			println("<----------------MAK-DEBUG-------->")
			sessionWiseMainReport.getReport().equals(expectedReport)
		where:
			countForSession|countNorthBoundVehicles
			4			   |4
	}
	
	def "File with 6 readings, with sequence A->B->A->B->A->A yield 1 SOUTH and 1 NORTH Vehicles"(int countForSession,int countNorthBoundVehicles){
		given:
			def inputLines = ["A638379","B638382","A638520","B638523","A1016488","A1016648"]	
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseMainReport = new SessionWiseMainReport(vehicleEntries)
			sessionWiseMainReport = new NorthDirectionWiseSubReport(sessionWiseMainReport)
			def sessionReport = "\t\tTotal:" + countForSession +"\n";
			def directionReport = "NORTH" + "\t\tTotal:" + countNorthBoundVehicles + "\n";
			def expectedReport = sessionReport + directionReport
		expect:
			sessionWiseMainReport.getReport().equals(expectedReport)
		where:
			countForSession|countNorthBoundVehicles
			2			   |1
	}
	
	def "File with 8 readings, with sequence A->B->A->B->A->B->A->B yield 2 South Vehicles"(int countForSession,int countNorthBoundVehicles){
		given:
			def inputLines = ["A638379","B638382","A638520","B638523","A1016488","B1016648","A1016588","B1016748"]	
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseMainReport = new SessionWiseMainReport(vehicleEntries)
			sessionWiseMainReport = new NorthDirectionWiseSubReport(sessionWiseMainReport)
			def sessionReport = "\t\tTotal:" + countForSession +"\n";
			def directionReport = "NORTH" + "\t\tTotal:" + countNorthBoundVehicles + "\n";
			def expectedReport = sessionReport + directionReport
		expect:
			sessionWiseMainReport.getReport().equals(expectedReport)
		where:
			countForSession|countNorthBoundVehicles
			2			   |0
	}
}
