package vc.transform.parser;

import static vc.common.constants.SensorData.LINE_FOUR;
import static vc.common.constants.SensorData.LINE_ONE;
import static vc.common.constants.SensorData.LINE_THREE;
import static vc.common.constants.SensorData.LINE_TWO;
import static vc.common.util.TimeUtil.parseMilliSecond;

import java.util.ArrayList;
import java.util.List;

import vc.common.exception.InAppropriateReadingException;
import vc.transform.validator.ITimeValidator;

public class SouthBoundEntriesTimeParser implements IParser{
	public ITimeValidator axleTimeValidator;

	public SouthBoundEntriesTimeParser(ITimeValidator axleTimeValidator) {
		this.axleTimeValidator = axleTimeValidator;
	}
	

	@Override
	public List<Integer> parse(List<String> fromSensorData) throws InAppropriateReadingException{
		List<Integer> axleParsedTimings = new ArrayList<Integer>();
			final int frontAxleSensor1Time = parseMilliSecond(fromSensorData.get(LINE_ONE));
			final int frontAxleSensor2Time = parseMilliSecond(fromSensorData.get(LINE_TWO));
			final int rearAxleSensor1Time =  parseMilliSecond(fromSensorData.get(LINE_THREE));
			final int rearAxleSensor2Time =  parseMilliSecond(fromSensorData.get(LINE_FOUR));
			final int frontAxleParsedTime = getAverage(frontAxleSensor1Time, frontAxleSensor2Time);
			final int rearAxleParsedTime = getAverage(rearAxleSensor1Time, rearAxleSensor2Time);
			if (axleTimeValidator.validateTime(frontAxleParsedTime, rearAxleParsedTime)){
				axleParsedTimings.add(frontAxleParsedTime);
				axleParsedTimings.add(rearAxleParsedTime);
			}
		if (axleParsedTimings.isEmpty()) 
			throw new InAppropriateReadingException("Invalid Reading");
		return axleParsedTimings;
	}
	
	private int getAverage(final int sensor1Time, final int sensor2Time) {
		return (sensor1Time + sensor2Time)/2;
	}

}