package vc.report.generator

import java.awt.Component.BaselineResizeBehavior
import java.rmi.activation.ActivationSystem
import java.util.List

import spock.lang.Specification
import vc.report.customiser.DefaultReportCustomiser
import vc.transform.classifier.DirectionClassifier
import vc.transform.core.SensorDataTransMutator
import vc.transform.factory.TimeParserFactory
import static vc.system.config.Interval.HALF_A_DAY;
import static vc.system.config.Interval.AN_HOUR;
import static vc.report.util.SessionManagementUtil.getSessionVehicleEntries;

class VehicleEntryReportGeneratorSpec extends Specification{
	
	DefaultReportCustomiser reportCustomiser = Mock(DefaultReportCustomiser)
	VehicleEntryReportGenerator reportGenerator = new VehicleEntryReportGenerator(reportCustomiser);
	def sensorDataParser = new TimeParserFactory();
	def directionClassifier = new DirectionClassifier();
	def transMutator = new SensorDataTransMutator(sensorDataParser,directionClassifier)
	
	def "For a Half day interval Customised report has to be generated for two sessions"(){
		given:
			List inputLines=["A98186","A98333","A499718","A499886"];
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			reportGenerator.setVehicleEntries(vehicleEntries)
		when:
			reportGenerator.printReport(HALF_A_DAY.getMinutes(),false)
		then:
			2 * reportCustomiser.getCustomisedReport(_,_)
	}
	def "For an hour interval Customised report has to be generated for 24 sessions"(){
		given:
			List inputLines=["A98186","A98333","A499718","A499886"];
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
			reportGenerator.setVehicleEntries(vehicleEntries)
		when:
			reportGenerator.printReport(AN_HOUR.getMinutes(),false)
		then:
			24 * reportCustomiser.getCustomisedReport(_,_)
	}
}
