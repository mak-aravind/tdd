package vc.transform.classifier

import java.util.List

import spock.lang.Specification
import vc.common.model.Direction
import static java.util.Collections.EMPTY_LIST
import static vc.common.model.Direction.NORTH
import static vc.common.model.Direction.SOUTH

class DirectionClassifierSpec extends Specification{
	def directionClassifier = new DirectionClassifier();
	def "File with ONLY 2 readings, with sequence A->B yield NIL"(List inputLines,
		List result){
		expect:
			def readings = directionClassifier.getEntriesToFindDirection(inputLines)
			readings == result
		where:
			inputLines			 |result
			["A638379","B638382"]|EMPTY_LIST
	}
	
	def "File with ONLY 2 readings, with sequence B->A yield NIL"(List inputLines,
		List result){
		expect:
			def readings = directionClassifier.getEntriesToFindDirection(inputLines)
			readings == result
		where:
			inputLines			 |result
			["B638379","A638382"]|EMPTY_LIST
	}
	
	def "File with ONLY 2 readings, both starts with A yield NORTH"(List inputLines,
		Direction expectedDirection){
		expect:
			def readings = directionClassifier.getEntriesToFindDirection(inputLines)
			Direction.fromListSize(readings.size()) == expectedDirection
		where:
			inputLines			 |expectedDirection
			["A268981","A269123"]|NORTH
	}
	
	def "File with ONLY 3 readings, with sequence A->B->A yield NIL"(List inputLines,
		List result){
		expect:
			def readings = directionClassifier.getEntriesToFindDirection(inputLines)
			readings == result
		where:
			inputLines			 			|result
			["A638379","B638382","A638520"]	|EMPTY_LIST
	}
	
	def "File with ONLY 4 readings, with sequence A->B->A->B yield SOUTH"(List inputLines,
		Direction expectedDirection){
		expect:
			def readings = directionClassifier.getEntriesToFindDirection(inputLines)
			Direction.fromListSize(readings.size()) == expectedDirection
		where:
			inputLines			 						|expectedDirection
			["A638379","B638382","A638520","B638523"]	|SOUTH
	}
	def "File with ONLY 4 readings, with sequence A->A->A->B yield NORTH"(List inputLines,
		Direction expectedDirection){
		expect:
			def readings = directionClassifier.getEntriesToFindDirection(inputLines)
			Direction.fromListSize(readings.size()) == expectedDirection
		where:
			inputLines			 						|expectedDirection
			["A268981","A269123","A638520","B638523"]	|NORTH
	}
	def "File with ONLY 4 readings, with sequence A->A->A->A yield NORTH"(List inputLines,
		Direction expectedDirection){
		expect:
			def readings = directionClassifier.getEntriesToFindDirection(inputLines)
			Direction.fromListSize(readings.size()) == expectedDirection
		where:
			inputLines			 					|expectedDirection
			["A98186","A98333","A499718","A499886"]	|NORTH
	}
}