package vc.transform.core

import java.awt.image.BandedSampleModel
import java.util.List

import spock.lang.Specification
import vc.common.model.Direction
import vc.transform.classifier.DirectionClassifier
import vc.transform.factory.TimeParserFactory
import static java.util.Collections.EMPTY_LIST
import static vc.common.model.Direction.NORTH;

class SensorDataTransMutator1or2or3ReadingsSpec extends Specification{


	def sensorDataParser = new TimeParserFactory();
	def directionClassifier = new DirectionClassifier();
	def transMutator = new SensorDataTransMutator(sensorDataParser,directionClassifier)

	def "File without minimum observations"(){
		given:
		def fileParsedInputLines = ["A98186"]
		when:
		def vehicleEntries = transMutator.getVehicalEntries(fileParsedInputLines)
		then:
		vehicleEntries == EMPTY_LIST
	}

	def "File with nil observations"(){
		given:
		def fileParsedInputLines = EMPTY_LIST
		when:
		def vehicleEntries = transMutator.getVehicalEntries(fileParsedInputLines)
		then:
		vehicleEntries == EMPTY_LIST
	}

	def "File with ONLY 2 readings first one starts with A and another with B should result empty list"(){
		given:
		def fileParsedInputLines = ["A98186", "B98333"]
		when:
		def vehicleEntries = transMutator.getVehicalEntries(fileParsedInputLines)
		then:
		vehicleEntries == EMPTY_LIST
	}

	def "File with ONLY 2 readings, first one starts with B and another with A should result empty list"(){
		given:
		def fileParsedInputLines = ["B98186", "A98333"]
		when:
		def vehicleEntries = transMutator.getVehicalEntries(fileParsedInputLines)
		then:
		vehicleEntries == EMPTY_LIST
	}

	def "File with ONLY 2 readings, both starts with B should result empty list"(){
		given:
		def fileParsedInputLines = ["B98186", "B98333"]
		when:
		def vehicleEntries = transMutator.getVehicalEntries(fileParsedInputLines)
		then:
		vehicleEntries == EMPTY_LIST
	}


	def "File with ONLY 3 readings, with sequence A->B->A yield NIL"(List inputLines,
			int expectedVehicleCount){
		given:
		def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
		vehicleEntries.size() == expectedVehicleCount;
		where:
		inputLines			 			|expectedVehicleCount
		["A638379","B638382","A638520"]	|0
	}
	
	def "File with ONLY 2 readings, both starts with A should be single north bound vehicle"(List inputLines,
		Direction expectedDirection, int expectedVehicleCount){
		given:
			def vehicleEntries = transMutator.getVehicalEntries(inputLines)
		expect:
			vehicleEntries.size() == expectedVehicleCount;
			def entry = vehicleEntries.get(0)
			entry.getDirection() == expectedDirection
		where:
		inputLines			 	|expectedDirection	|expectedVehicleCount
		["A268981", "A269123"]	|NORTH				|1
	}

}
