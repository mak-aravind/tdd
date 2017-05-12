package vc.transform.parser;

import static vc.common.constants.SensorData.LINE_ONE;
import static vc.common.constants.SensorData.LINE_TWO;
import static vc.common.util.TimeUtil.parseMilliSecond;

import java.util.ArrayList;
import java.util.List;

import vc.common.exception.InAppropriateReadingException;
import vc.transform.validator.ITimeValidator;

public class NorthBoundEntriesTimeParser implements IParser{
	public ITimeValidator axleTimeValidator;

	public NorthBoundEntriesTimeParser(ITimeValidator axleTimeValidator) {
		this.axleTimeValidator = axleTimeValidator;
	}

	@Override
	public List<Integer> parse(List<String> fromSensorData) throws InAppropriateReadingException {
		final List<Integer> axleParsedTimings = new ArrayList<Integer>();
		final int frontAxleSensor1Time = parseMilliSecond(fromSensorData.get(LINE_ONE));
		final int rearAxleSensor1Time = parseMilliSecond(fromSensorData.get(LINE_TWO));
		if(axleTimeValidator.validateTime(frontAxleSensor1Time, rearAxleSensor1Time)){
			axleParsedTimings.add(frontAxleSensor1Time);
			axleParsedTimings.add(rearAxleSensor1Time);
		}
		if (axleParsedTimings.isEmpty()) 
			throw new InAppropriateReadingException("Invalid Reading");
		return axleParsedTimings;
	}
}