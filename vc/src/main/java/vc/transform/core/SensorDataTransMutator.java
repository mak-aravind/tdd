package vc.transform.core;

import static vc.common.util.DirectionUtil.hasRequiredReadings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import vc.common.dto.VehicleEntry;
import vc.common.exception.InAppropriateReadingException;
import vc.common.model.Direction;
import vc.transform.classifier.IClassifier;
import vc.transform.factory.TimeParserFactory;

public class SensorDataTransMutator {

	private int currentDay = 0;
	private Date readingStartTimeForDay = new Date(currentDay);
	private TimeParserFactory sensorDataParser;
	private final IClassifier directionClassifier;

	public SensorDataTransMutator(TimeParserFactory sensorDataParser,IClassifier directionClassifier) {
		this.sensorDataParser = sensorDataParser;
		this.directionClassifier = directionClassifier;
	}

	public List<VehicleEntry> getVehicalEntries(List<String> fromSensorData){
		final List<VehicleEntry> vehicleEntries = new ArrayList<VehicleEntry>();
		
		if (!hasRequiredReadings(fromSensorData.size()))
			return Collections.emptyList();
		
		while (hasRequiredReadings(fromSensorData.size())) {
			final List<String> readingsToTransform = directionClassifier.getEntriesToFindDirection(fromSensorData);
			final Direction direction = Direction.fromListSize(readingsToTransform.size());
			if (null == direction) return Collections.emptyList();
			try {
				transformSensorData(readingsToTransform, vehicleEntries, direction);
			} catch (InAppropriateReadingException e) {
				return Collections.emptyList();
			}
			fromSensorData = fromSensorData.subList(direction.getMinimumReadings(), fromSensorData.size());
		}
		return vehicleEntries;
	}

	private void transformSensorData(List<String> readingsToTransform, List<VehicleEntry> toVehicleEntries,
			Direction direction) throws InAppropriateReadingException {
		final List<Integer> axleTimings = sensorDataParser.getParser(direction)
														  .parse(readingsToTransform);
		VehicleEntry entry = new VehicleEntry(axleTimings.get(0), axleTimings.get(1), direction, this.currentDay);
		updateDayIfNeeded(entry);
		toVehicleEntries.add(entry);
	}
	
	private void updateDayIfNeeded(VehicleEntry entry) {
		Date currentEntryTime = entry.entryTime();
		// Check whether currentEntryTime is before the readingStartTimeForDay
		if (currentEntryTime.compareTo(readingStartTimeForDay) < 0) {
			this.currentDay++;
			entry.setDay(currentDay);
		}
		readingStartTimeForDay = currentEntryTime;
	}
}