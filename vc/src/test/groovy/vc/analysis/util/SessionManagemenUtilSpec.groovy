package vc.analysis.util

import spock.lang.Specification
import vc.system.config.Interval;
import vc.common.exception.InValidIntervalException
import static vc.report.util.SessionManagementUtil.createSessionsInADay

class SessionManagemenUtilSpec extends Specification{
	
	def "intervals that cannot be distribued evenly in a day result empty sessions"(){
		given:
			def interval = 709
		when:
			 createSessionsInADay(interval)
		then:
			thrown(InValidIntervalException)
	}
	
	def "720 minutes = 12 hours which is morning and evening session - give 2 sessions"(Interval interval, int expectedSize){
		expect:
			def result = createSessionsInADay(interval.getMinutes())
			result.size() == expectedSize 
		where:
			interval							|expectedSize
			Interval.HALF_A_DAY	 				|2
	}
	
	def "60 minutes = 1 hour which is 24 hours/day - give 24 sessions"(Interval interval, int expectedSize){
		expect:
			def result = createSessionsInADay(interval.getMinutes())
			result.size() == expectedSize
		where:
			interval		|expectedSize
			Interval.AN_HOUR	|24
	}
	
	def "30,20 and 15 minutes should give  24*2,24*3 and 24*4 sessions"(Interval interval, int expectedSize){
		expect:
			def result = createSessionsInADay(interval.getMinutes())
			result.size() == expectedSize
		where:
			interval				|expectedSize
			Interval.THIRTY_MINUTES	|24 * 2
			Interval.TWENTY_MINUTES	|24 * 3
			Interval.FIFTEEN_MINUTES|24 * 4
	}
	
	
	def "HH:mm:ss format checking with 1 sesion"(int expectedSessions,String session1){
		given:
			int interval = 1440
		expect:
			def sessions = createSessionsInADay(interval)
			sessions.size() == expectedSessions
			sessions.get(0).toString().equals(session1);
		where:
			expectedSessions	|session1						
			1					|"Session 00:00:00 to 00:00:00"
	}
	
	def "HH:mm:ss format checking with 2 sessions"(int expectedSessions,String session1, String session2){
		given:
			Interval interval = Interval.HALF_A_DAY
		expect:
			def sessions = createSessionsInADay(interval.getMinutes())
			sessions.size() == expectedSessions
			sessions.get(0).toString().equals(session1);
			sessions.get(1).toString().equals(session2);
		where:
			expectedSessions	|session1						|session2
			2					|"Session 00:00:00 to 12:00:00"	|"Session 12:00:00 to 00:00:00"
	}
	def "HH:mm:ss format checking with 4 sessions"(int expectedSessions,String session1, String session2,String session3, String session4){
		given:
			int interval = 360
		expect:
			def sessions = createSessionsInADay(interval)
			sessions.size() == expectedSessions
			sessions.get(0).toString().equals(session1);
			sessions.get(1).toString().equals(session2);
			sessions.get(2).toString().equals(session3);
			sessions.get(3).toString().equals(session4);
		where:
			expectedSessions|session1						|session2						|session3						|session4
			4				|"Session 00:00:00 to 06:00:00"	|"Session 06:00:00 to 12:00:00"	|"Session 12:00:00 to 18:00:00"	|"Session 18:00:00 to 00:00:00"
	}
}

