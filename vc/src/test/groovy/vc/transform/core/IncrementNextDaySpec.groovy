package vc.transform.core

import java.util.List

import spock.lang.Specification
import vc.transform.classifier.DirectionClassifier
import vc.transform.factory.TimeParserFactory
import static java.util.Collections.EMPTY_LIST

class IncrementNextDaySpec extends Specification{
	def sensorDataParser = new TimeParserFactory();
	def directionClassifier = new DirectionClassifier();
	def transMutator = new SensorDataTransMutator(sensorDataParser,directionClassifier)


	def "Should NOT Increment Day if we encouter an entry time GREATER than previous entry time"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		def entry1 = vehicleEntries.get(0)
		def entry2 = vehicleEntries.get(1)
		entry1.getDay() == 0
		entry2.getDay() == 0
		where:
		inputLines			 						|expectedVehicleCount
		["A98186","A98333","A499718","A499886"]		|2
	}

	def "Should Increment Day if we encouter an entry time LESSER than previous entry time"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		def entry1 = vehicleEntries.get(0)
		def entry2 = vehicleEntries.get(1)
		entry1.getDay() == 0
		entry2.getDay() == 1
		where:
		inputLines			 						|expectedVehicleCount
		["A499718","A499886","A98186","A98333"]		|2
	}


	def "Should get last entry's day as three and should increment day thrice"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		def entry1 = vehicleEntries.get(0)
		def entry2 = vehicleEntries.get(1)
		def entry3 = vehicleEntries.get(2)
		def entry4 = vehicleEntries.get(3)
		entry1.getDay() == 0
		entry2.getDay() == 1
		entry3.getDay() == 2
		entry4.getDay() == 3
		where:
		inputLines			 																	|expectedVehicleCount
		["A1016588","A1016748","A1016488","A1016648","A638520","A638523","A638379","A638382"]	|4
	}

	def "Should get last entry's day as 2 and should increment day twice"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		def entry1 = vehicleEntries.get(0)
		def entry2 = vehicleEntries.get(1)
		def entry3 = vehicleEntries.get(2)
		def entry4 = vehicleEntries.get(3)
		entry1.getDay() == 0
		entry2.getDay() == 1
		entry3.getDay() == 1
		entry4.getDay() == 2
		where:
		inputLines			 														|expectedVehicleCount
		["A12345","A12350","A12245","A12250","A12260","A12265","A11345","A11350"]	|4

	}

	def "In-Appropriate timing: front Axle time greater than rear axle time"(List inputLines, List result){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries == result
		where:
		inputLines			 		|result
		["A269123", "A268981"]		|EMPTY_LIST
	}

	def "File with ONLY 4 readings, A->B->A->B with Inappropriate timing"(List inputLines, List result){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		print("<MAK-DEBUG>" + vehicleEntries.size())
		expect:
		vehicleEntries == result
		where:
		inputLines			 							|result
		["A269040","B269050","A269035","B269045"]		|EMPTY_LIST
	}
}
