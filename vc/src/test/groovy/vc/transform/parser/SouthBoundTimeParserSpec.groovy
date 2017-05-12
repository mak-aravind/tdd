package vc.transform.parser

import vc.common.exception.InAppropriateReadingException
import spock.lang.Specification
import vc.transform.validator.AxleTimeValidator

class SouthBoundTimeParserSpec extends Specification{

	def "File with ONLY 4 readings, A->B->A->B with Inappropriate timing"(){
		given:
			def inputLines = ["A269040","B269050","A269035","B269045"]
			def axleTimeValidator = new AxleTimeValidator();
			def timeParser = new SouthBoundEntriesTimeParser(axleTimeValidator)
		when:
			def axleParsedTimings = timeParser.parse(inputLines)
		then:
			thrown(InAppropriateReadingException)
	}
	
	def "File with ONLY 4 readings, A->B->A->B with proper timing"(){
		given:
			def inputLines = ["A269040","B269050","A269055","B269060"]
			def axleTimeValidator = new AxleTimeValidator();
			def timeParser = new SouthBoundEntriesTimeParser(axleTimeValidator)
			def axleParsedTimings = timeParser.parse(inputLines)
		expect:
			axleParsedTimings.size() == 2
	}
}
