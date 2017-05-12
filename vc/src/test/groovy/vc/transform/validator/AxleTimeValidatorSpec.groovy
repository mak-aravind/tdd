package vc.transform.validator

import spock.lang.Specification

class AxleTimeValidatorSpec extends Specification{
	
	def "Inappropriate timing: front Axle time and rear time both are same"(int frontAxleTime,int rearAxleTime, boolean result){
		given:
			def axleTimeValidator = new AxleTimeValidator();
		expect:
			axleTimeValidator.validateTime(frontAxleTime, rearAxleTime) == result;
		where:
			frontAxleTime|	rearAxleTime|result
			0			 |	0			|false
			-1			 |	-1			|false			
	}
	def "Inappropriate timing: A negative front Axle time is less than rear time"(int frontAxleTime,int rearAxleTime, boolean result){
		given:
			def axleTimeValidator = new AxleTimeValidator();
		expect:
			axleTimeValidator.validateTime(frontAxleTime, rearAxleTime) == result;
		where:
			frontAxleTime|	rearAxleTime|result
			-1			 |	0			|false
	}
	def "InAppropriate timing: front Axle time greater than rear axle time"(int frontAxleTime,int rearAxleTime, boolean result){
		given:
			def axleTimeValidator = new AxleTimeValidator();
		expect:
			axleTimeValidator.validateTime(frontAxleTime, rearAxleTime) == result;
		where:
			frontAxleTime|	rearAxleTime	|result
			4356		 |	3125			|false
			0220		 |	0100			|false
	}
	def "Appropriate timing: front Axle time lesser than rear axle time"(int frontAxleTime,int rearAxleTime, boolean result){
		given:
			def axleTimeValidator = new AxleTimeValidator();
		expect:
			axleTimeValidator.validateTime(frontAxleTime, rearAxleTime) == result;
		where:
			frontAxleTime|	rearAxleTime	|result
			3125		 |	4356			|true
			0100		 |	0220			|true
	}
}
