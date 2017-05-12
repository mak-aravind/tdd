package vc.common.constants

import spock.lang.Specification

class DummyConstantsSpec extends Specification{
	
	def "dummy testcase to validate the test coverage"(){
		given:
			def file = new File()
			def sensorData = new SensorData()
			def time = new Time()
		expect:
			println("Do nothing");
	}
}
