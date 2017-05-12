package vc.transform.validator

import java.util.List

import spock.lang.Specification

class AxleSequenceValidatorSpec extends Specification{
	def "File with ONLY 4 readings, with sequence A->B->A->A yield false"(List inputLines,
	boolean expectedResult){
		expect:
			def sequenceValidator = new AxleSequenceValidator()
			sequenceValidator.isOrdered(inputLines) == expectedResult
		where:
			inputLines			 						|expectedResult
			["A268981","B269123","A638520","A638523"]	|false
	}
	def "File with ONLY 4 readings, with sequence A->B->B->B yield false"(List inputLines,
		boolean expectedResult){
			expect:
				def sequenceValidator = new AxleSequenceValidator()
				sequenceValidator.isOrdered(inputLines) == expectedResult
			where:
				inputLines			 						|expectedResult
				["A268981","B269123","B638520","A638523"]	|false
	}
	def "File with ONLY 4 readings, with sequence B->A->B->A yield false"(List inputLines,
		boolean expectedResult){
			expect:
				def sequenceValidator = new AxleSequenceValidator()
				sequenceValidator.isOrdered(inputLines) == expectedResult
			where:
				inputLines			 						|expectedResult
				["A268981","B269123","B638520","A638523"]	|false
	}
	def "File with ONLY 4 readings, with sequence A->B->A->B yield true"(List inputLines,
			boolean expectedResult){
			expect:
				def sequenceValidator = new AxleSequenceValidator()
				sequenceValidator.isOrdered(inputLines) == expectedResult
			where:
				inputLines			 						|expectedResult
				["A638379","B638382","A638520","B638523"]	|true
	}
}
