package vc.transform.validator;

import static vc.common.constants.SensorData.LINE_FOUR;
import static vc.common.constants.SensorData.LINE_ONE;
import static vc.common.constants.SensorData.LINE_THREE;
import static vc.common.constants.SensorData.LINE_TWO;
import static vc.common.constants.SensorData.SENSOR1_PREFIX;
import static vc.common.constants.SensorData.SENSOR2_PREFIX;

import java.util.List;

public class AxleSequenceValidator implements ISequenceValidator {

	@Override
	public boolean isOrdered(final List<String> listToValidate) {
		boolean ordered = isOrderOfEntriesValid(listToValidate.get(LINE_ONE), 
				listToValidate.get(LINE_TWO), 
				listToValidate.get(LINE_THREE), 
				listToValidate.get(LINE_FOUR));
		
		return ordered;
	}
	
	public static boolean isOrderOfEntriesValid(final String frontAxleSensor1Entry, final String frontAxleSensor2Entry,
			final String rearAxleSensor1Entry, final String rearAxleSensor2Entry) {
		return frontAxleSensor1Entry.startsWith(SENSOR1_PREFIX) && frontAxleSensor2Entry.startsWith(SENSOR2_PREFIX)
				&& rearAxleSensor1Entry.startsWith(SENSOR1_PREFIX) && rearAxleSensor2Entry.startsWith(SENSOR2_PREFIX);
	}
	
}
