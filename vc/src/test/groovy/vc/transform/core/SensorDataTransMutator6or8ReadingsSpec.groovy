package vc.transform.core

import java.util.List

import spock.lang.Specification
import vc.transform.classifier.DirectionClassifier
import vc.transform.factory.TimeParserFactory
import static vc.common.model.Direction.NORTH;
import static vc.common.model.Direction.SOUTH;

class SensorDataTransMutator6or8ReadingsSpec extends Specification{

	def sensorDataParser = new TimeParserFactory();
	def directionClassifier = new DirectionClassifier();
	def transMutator = new SensorDataTransMutator(sensorDataParser,directionClassifier)

	def "File with 6 readings, with sequence A->A->A->A->A->A yield 3 NORTH bound Vehicles"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		def entry1 = vehicleEntries.get(0)
		def entry2 = vehicleEntries.get(1)
		def entry3 = vehicleEntries.get(2)
		entry1.getDirection() == NORTH
		entry2.getDirection() == NORTH
		entry3.getDirection() == NORTH
		where:
		inputLines			 												|expectedVehicleCount
		["A98186","A98333","A499718","A499886","A1016488","A1016648"]		|3
	}
	def "File with 6 readings, with sequence A->B->A->B->A->A yield 1 SOUTH and 1 NORTH Vehicles"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		def entry1 = vehicleEntries.get(0)
		def entry2 = vehicleEntries.get(1)
		entry1.getDirection() == SOUTH
		entry2.getDirection() == NORTH
		where:
		inputLines			 												|expectedVehicleCount
		["A638379","B638382","A638520","B638523","A1016488","A1016648"]		|2
	}
	def "File with 6 readings, with sequence A->B->A->B->A->B yield Zero Vehicles"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		where:
		inputLines			 												|expectedVehicleCount
		["A638379","B638382","A638520","B638523","A1016488","B1016648"]		|0
	}
	def "File with 8 readings, with sequence A->B->A->B->A->B->A->B yield 2 South Vehicles"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		def entry1 = vehicleEntries.get(0)
		def entry2 = vehicleEntries.get(1)
		entry1.getDirection() == SOUTH
		entry2.getDirection() == SOUTH
		where:
		inputLines			 																	|expectedVehicleCount
		["A638379","B638382","A638520","B638523","A1016488","B1016648","A1016588","B1016748"]	|2
	}
	def "File with 8 readings, with sequence A->A->A->A->A->A->A->A yield 4 North Vehicles"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		def entry1 = vehicleEntries.get(0)
		def entry2 = vehicleEntries.get(1)
		def entry3 = vehicleEntries.get(2)
		def entry4 = vehicleEntries.get(3)
		entry1.getDirection() == NORTH
		entry2.getDirection() == NORTH
		entry3.getDirection() == NORTH
		entry4.getDirection() == NORTH
		where:
		inputLines			 																	|expectedVehicleCount
		["A638379","A638382","A638520","A638523","A1016488","A1016648","A1016588","A1016748"]	|4
	}
}






