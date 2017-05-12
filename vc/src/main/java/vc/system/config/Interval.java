package vc.system.config;

public enum Interval {
	FIFTEEN_MINUTES(15),TWENTY_MINUTES(20),THIRTY_MINUTES(30),AN_HOUR(60),HALF_A_DAY(720);
	
	private int minutes;
	
	private Interval(int minutes) { this.minutes = minutes; }
	
	public int getMinutes(){
		return minutes;
	}
}
