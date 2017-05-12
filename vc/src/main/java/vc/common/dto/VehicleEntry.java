package vc.common.dto;

import java.util.Date;

import vc.common.model.Direction;

public class VehicleEntry {
	private final int frontAxleTime;
	private final int rearAxleTime;
	private final Direction direction;
	private int day;
	
	public VehicleEntry(int frontAxleTime, int rearAxleTime, Direction direction, int day) {
	        this.frontAxleTime = frontAxleTime;
	        this.rearAxleTime = rearAxleTime;
	        this.direction = direction;
	        this.day = day;
	}

	public Direction getDirection() {
		return direction;
	}
	
	public void setDay(final int day){
		this.day = day;
	}
	
	public int getDay() {
		return day;
	}
	
	public Date entryTime() {
		int averageTime = (frontAxleTime + rearAxleTime)/2;
		return new Date(averageTime);
	}
	
	public int timeTookToCrossSensors(){
		return rearAxleTime - frontAxleTime;
	}
}
