package vc.transform.classifier;

import static vc.common.constants.SensorData.LINE_ONE;
import static vc.common.constants.SensorData.LINE_TWO;
import static vc.common.constants.SensorData.SENSOR1_PREFIX;
import static vc.common.model.Direction.NORTH;
import static vc.common.model.Direction.SOUTH;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import vc.common.model.Direction;
import vc.transform.validator.AxleSequenceValidator;
import vc.transform.validator.ISequenceValidator;

public class DirectionClassifier implements IClassifier{
	
	private ISequenceValidator axleSequenceValidator = new AxleSequenceValidator();

	public List<String> getEntriesToFindDirection(final List<String> fromSensorData) {
		String firstReading =  fromSensorData.get(LINE_ONE); 
		String secondReading = fromSensorData.get(LINE_TWO);
		
		if (firstReading.startsWith(SENSOR1_PREFIX) 
				&& secondReading.startsWith(SENSOR1_PREFIX))
			return extractEntries(fromSensorData,NORTH);
		else if(fromSensorData.size() >= SOUTH.getMinimumReadings() 
				&& axleSequenceValidator.isOrdered(fromSensorData))
			return extractEntries(fromSensorData,SOUTH);
		else
			return Collections.emptyList();
	}
	
	private List<String> extractEntries(final List<String> fromSensorData, Direction direction) {
		return fromSensorData.stream().limit(direction.getMinimumReadings()).collect(Collectors.toList());
	}
}