package vc.report.section

import spock.lang.Specification
import vc.transform.classifier.DirectionClassifier
import vc.transform.core.SensorDataTransMutator
import vc.transform.factory.TimeParserFactory

class DayWiseSubReportSpec extends Specification{
	
	def sensorDataParser = new TimeParserFactory();
	def directionClassifier = new DirectionClassifier();
	def transMutator = new SensorDataTransMutator(sensorDataParser,directionClassifier)
	
	def "2 entries for Day-1"(int day1,int day2,int day3, int day4, int day5){
		given:
			def counts = [day1,day2,day3,day4,day5]
			def inputLines = ["A98186","A98333","A499718","A499886"]
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseMainReport = new SessionWiseMainReport(vehicleEntries)
			def dayWiseSubReport = new DayWiseSubReport(sessionWiseMainReport);
			sessionWiseMainReport = new DayWiseSubReport(sessionWiseMainReport);
		expect:
			def expectedSessonReportLine1 = "\t\tTotal:" + vehicleEntries.size() +"\n";
			def expectedColumnHeadings = dayWiseSubReport.getColumnHeadings();
			def expectedCountForEachDay = dayWiseSubReport.getformattedString(counts);
			def expectedReport = expectedSessonReportLine1 + expectedColumnHeadings + expectedCountForEachDay
			sessionWiseMainReport.getReport().equals(expectedReport)
			println("<----------------MAK-DEBUG-------->")
			print(expectedReport)
			println("<----------------MAK-DEBUG-------->")
		where:
			day1	|day2	|day3	|day4	|day5
			2		|0		|0		|0		|0
	}
	
	def "1 entry for Day-1 and 1 entry for Day-2"(int day1,int day2,int day3, int day4, int day5){
		given:
			def counts = [day1,day2,day3,day4,day5]
			def inputLines = ["A499718","A499886","A98186","A98333"]
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseMainReport = new SessionWiseMainReport(vehicleEntries)
			def dayWiseSubReport = new DayWiseSubReport(sessionWiseMainReport);
			sessionWiseMainReport = new DayWiseSubReport(sessionWiseMainReport);
		expect:
			def expectedSessonReportLine1 = "\t\tTotal:" + vehicleEntries.size() +"\n";
			def expectedColumnHeadings = dayWiseSubReport.getColumnHeadings();
			def expectedCountForEachDay = dayWiseSubReport.getformattedString(counts);
			def expectedReport = expectedSessonReportLine1 + expectedColumnHeadings + expectedCountForEachDay
			sessionWiseMainReport.getReport().equals(expectedReport)
		where:
			day1	|day2	|day3	|day4	|day5
			1		|1		|0		|0		|0
	}
	
	def "Except Day-5. Each Day encounters one entry"(int day1,int day2,int day3, int day4, int day5){
		given:
			def counts = [day1,day2,day3,day4,day5]
			def inputLines = ["A1016588","A1016748","A1016488","A1016648","A638520","A638523","A638379","A638382"]
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseReport = new SessionWiseMainReport(vehicleEntries)
			def dayWiseSubReport = new DayWiseSubReport(sessionWiseReport);
		expect:
			def expectedSessonReportLine1 = "\t\tTotal:" + vehicleEntries.size() +"\n";
			def expectedColumnHeadings = dayWiseSubReport.getColumnHeadings();
			def expectedCountForEachDay = dayWiseSubReport.getformattedString(counts);
			def expectedReport = expectedSessonReportLine1 + expectedColumnHeadings + expectedCountForEachDay
			dayWiseSubReport.getReport().equals(expectedReport)
		where:
			day1	|day2	|day3	|day4	|day5
			1		|1		|1		|1		|0
	}
	
	def "One entry Day-1, Two entries for Day-2, One entry for Day-3"(int day1,int day2,int day3, int day4, int day5){
		given:
			def counts = [day1,day2,day3,day4,day5]
			def inputLines = ["A12345","A12350","A12245","A12250","A12260","A12265","A11345","A11350"]
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			def sessionWiseMainReport = new SessionWiseMainReport(vehicleEntries)
			def dayWiseSubReport = new DayWiseSubReport(sessionWiseMainReport);
			sessionWiseMainReport = new DayWiseSubReport(sessionWiseMainReport);
		expect:
			def expectedSessonReportLine1 = "\t\tTotal:" + vehicleEntries.size() +"\n";
			def expectedColumnHeadings = dayWiseSubReport.getColumnHeadings();
			def expectedCountForEachDay = dayWiseSubReport.getformattedString(counts);
			def expectedReport = expectedSessonReportLine1 + expectedColumnHeadings + expectedCountForEachDay
			sessionWiseMainReport.getReport().equals(expectedReport)
		where:
			day1	|day2	|day3	|day4	|day5
			1		|2		|1		|0		|0
	}

}
