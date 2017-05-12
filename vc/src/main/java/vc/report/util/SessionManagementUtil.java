package vc.report.util;

import static vc.common.constants.Time.HOURS_IN_A_DAY;
import static vc.common.constants.Time.MILLISECONDS_IN_A_SECOND;
import static vc.common.constants.Time.MINUTES_IN_A_HOUR;
import static vc.common.constants.Time.SECONDS_IN_A_MINUTE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import vc.common.dto.VehicleEntry;
import vc.common.exception.InValidIntervalException;
import vc.common.model.Session;

public class SessionManagementUtil {

	public static List<VehicleEntry> getSessionVehicleEntries(List<VehicleEntry> vehicleEntries, Session session) {
        return vehicleEntries.stream()
        					.filter(entry -> isValidForSession(entry, session))
        					.collect(Collectors.toList());
	}

	private static boolean isValidForSession(VehicleEntry entry, Session session) {
		return (entry.entryTime().compareTo(session.startTime) >= 0
				&& entry.entryTime().compareTo(session.endTime) < 0);
	}
	
    public static List<Session> createSessionsInADay(int intervalInMinutes) throws InValidIntervalException{
        List<Session> sessions = new ArrayList<>();
        int totalMinutesInADay = HOURS_IN_A_DAY * MINUTES_IN_A_HOUR;
        if (totalMinutesInADay % intervalInMinutes != 0){
            throw new InValidIntervalException("Day Cannot be evenly Distributed for : "+ intervalInMinutes);
        }
        for (int i=0; i < totalMinutesInADay/intervalInMinutes; i++){
            Date startTime = new Date(i * intervalInMinutes * SECONDS_IN_A_MINUTE * MILLISECONDS_IN_A_SECOND);
            Date endTime = new Date((i+1) * intervalInMinutes * SECONDS_IN_A_MINUTE * MILLISECONDS_IN_A_SECOND);

            sessions.add(new Session(startTime,endTime));
        }
        return sessions;
    }
}
