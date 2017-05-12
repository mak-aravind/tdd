package vc.common.util

import spock.lang.Specification
import static vc.common.util.TimeUtil.parseMilliSecond

class TimeUtilSpec extends Specification{

	def "expect -1 as invalid time if numbers appended are negative"(String input, int result){
		expect:
			parseMilliSecond(input) == result
		where:
			input			|result
			"A-1040434"		|-1
	}
	
	def "expect -1 as invalid time if non-numeric character appended"(){
		given:
			def input ="A124jk"
		expect:
			parseMilliSecond(input) == -1
	}
	
	def "expect the numbers after single Alphabet"(String input, int result){
		expect:
			parseMilliSecond(input) == result
		where:
			input		|result
			"A1040434"	|1040434
			"A2"		|2
			"B00000000"	|0
	}	
	// Other validations are done by the regex, used while parsing file : "^[AaBb][0-9]+$"
}
