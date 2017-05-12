package vc.transform.parser

import spock.lang.Specification
import vc.transform.validator.AxleTimeValidator
import vc.common.exception.InAppropriateReadingException

class NorthBoundTimeParserSpec extends Specification{

	def "File with ONLY 2 readings, both starts with A with Inappropriate timing"(){
		given:
			def inputLines = ["A269123","A268981"]
			def axleTimeValidator = new AxleTimeValidator();
			def timeParser = new NorthBoundEntriesTimeParser(axleTimeValidator)
		when:
			def axleParsedTimings = timeParser.parse(inputLines)
		then:
			thrown(InAppropriateReadingException)
	}
	
	def "File with ONLY 2 readings, both starts with A with PROPER timing"(){
		given:
			def inputLines = ["A268981","A269123"]
			def axleTimeValidator = new AxleTimeValidator();
			def timeParser = new NorthBoundEntriesTimeParser(axleTimeValidator)
			def axleParsedTimings = timeParser.parse(inputLines)
		expect:
			axleParsedTimings.size() == 2
	}
}
