package vc.transform.core

import java.util.List

import spock.lang.Specification
import vc.common.model.Direction
import vc.transform.classifier.DirectionClassifier
import vc.transform.factory.TimeParserFactory
import static vc.common.model.Direction.SOUTH;
import static vc.common.model.Direction.NORTH;

class SensorDataTransMutator4ReadingsSpec extends Specification{

	def sensorDataParser = new TimeParserFactory();
	def directionClassifier = new DirectionClassifier();
	def transMutator = new SensorDataTransMutator(sensorDataParser,directionClassifier)

	def "File with ONLY 4 readings starts with NORTH but not match for SOUTH, with sequence A->A->A->B yield no Vehicles"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		where:
		inputLines			 						|expectedVehicleCount
		["A268981","A269123","A638520","B638523"]	|0
	}

	def "SOUTH WRONG SEQUENCE File with ONLY 4 readings, with sequence A->B->A->A yield no Vehicles"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		where:
		inputLines			 						|expectedVehicleCount
		["A268981","B269123","A638520","A638523"]	|0
	}

	def "SOUTH WRONG SEQUENCE File with ONLY 4 readings, with sequence B->A->B->A yield no Vehicles"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		where:
		inputLines			 							|expectedVehicleCount
		["B1201542","A1624044","B1624047","A1624188"]	|0
	}

	def "File with ONLY 4 readings, with sequence A->A->A->A yield 2 NORTH bound Vehicles"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		def entry1 = vehicleEntries.get(0)
		def entry2 = vehicleEntries.get(1)
		entry1.getDirection() == NORTH
		entry2.getDirection() == NORTH
		where:
		inputLines			 						|expectedVehicleCount
		["A98186","A98333","A499718","A499886"]		|2
	}
	def "File with ONLY 4 readings and sequence as A->B->A->B should be single south bound vehicle"(List inputLines,
			Direction expectedDirection, int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount
		def entry = vehicleEntries.get(0)
		entry.getDirection() == expectedDirection
		where:
		inputLines								 |expectedDirection	|expectedVehicleCount
		["A638379","B638382","A638520","B638523"]|SOUTH				|1
	}
}
