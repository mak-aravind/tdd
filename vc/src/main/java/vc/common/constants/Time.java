package vc.common.constants;

public class Time {
	/*private Time(){
		throw new AssertionError();//Big Bang when instantiated from native class(locally)
	}*/
	
	public Time(){}//for code coverage
	public static final int HOURS_IN_A_DAY = 24;
    public static final int MINUTES_IN_A_HOUR = 60;
    public static final int SECONDS_IN_A_MINUTE = 60;
    public static final int MILLISECONDS_IN_A_SECOND = 1000;
    public static final int MAX_MILLISECONDS_IN_A_DAY = HOURS_IN_A_DAY * MINUTES_IN_A_HOUR * SECONDS_IN_A_MINUTE * MILLISECONDS_IN_A_SECOND;
}

