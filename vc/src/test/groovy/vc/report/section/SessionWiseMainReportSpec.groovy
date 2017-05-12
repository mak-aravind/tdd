package vc.report.section

import java.util.List
import vc.transform.classifier.DirectionClassifier
import vc.transform.core.SensorDataTransMutator
import vc.transform.factory.TimeParserFactory

import spock.lang.Specification

class SessionWiseMainReportSpec extends Specification{
	
	def sensorDataParser = new TimeParserFactory();
	def directionClassifier = new DirectionClassifier();
	def transMutator = new SensorDataTransMutator(sensorDataParser,directionClassifier)
	
	
	def "SOUTH WRONG SEQUENCE File with ONLY 4 readings, with sequence A->B->A->A yield no Vehicles"(List inputLines,
		int countForSession){
		given:
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseReport = new SessionWiseMainReport(vehicleEntries)
			def expectedReport = "\t\tTotal:" + countForSession + "\n"
		expect:
			println("<----------------MAK-DEBUG-------->")
			print(expectedReport)
			println("<----------------MAK-DEBUG-------->")
			sessionWiseReport.getReport().equals(expectedReport);
		where:
		inputLines			 						|countForSession
		["A268981","B269123","A638520","A638523"]	|0
	}
	def "File with ONLY 4 readings and sequence as A->B->A->B should be single south bound vehicle"(List inputLines,int countForSession){
		given:
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseReport = new SessionWiseMainReport(vehicleEntries)
			def expectedReport = "\t\tTotal:" + countForSession + "\n"
		expect:
			sessionWiseReport.getReport().equals(expectedReport)
		where:
		inputLines								 |countForSession
		["A638379","B638382","A638520","B638523"]|1
	}
	
	def "File with 8 readings, with sequence A->A->A->A->A->A->A->A yield 4 Vehicles"(List inputLines,
		int countForSession){
		given:
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseReport = new SessionWiseMainReport(vehicleEntries)
			def expectedReport = "\t\tTotal:" + countForSession + "\n"
		expect:
			sessionWiseReport.getReport().equals(expectedReport);
		where:
		inputLines			 																	|countForSession
		["A638379","A638382","A638520","A638523","A1016488","A1016648","A1016588","A1016748"]	|4
	}	
}
