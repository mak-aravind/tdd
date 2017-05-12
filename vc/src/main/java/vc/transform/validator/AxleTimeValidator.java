package vc.transform.validator;

import static vc.common.constants.Time.MAX_MILLISECONDS_IN_A_DAY;

public class AxleTimeValidator implements ITimeValidator{
    public boolean validateTime(int frontAxleTime, int rearAxleTime){
        return frontAxleTime >= 0
	            && frontAxleTime < MAX_MILLISECONDS_IN_A_DAY
	            && rearAxleTime >= 0
	            && rearAxleTime < MAX_MILLISECONDS_IN_A_DAY
	            && frontAxleTime < rearAxleTime;
	}
}
