package vc.common.util;

import vc.common.model.Direction;

public class DirectionUtil {
	
	public static boolean hasRequiredReadings(final int listSize) {
		return listSize >= Direction.NORTH.getMinimumReadings();
	}
}
