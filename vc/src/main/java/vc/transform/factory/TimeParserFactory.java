package vc.transform.factory;

import vc.common.exception.InAppropriateReadingException;
import vc.common.model.Direction;
import vc.transform.parser.IParser;
import vc.transform.parser.NorthBoundEntriesTimeParser;
import vc.transform.parser.SouthBoundEntriesTimeParser;
import vc.transform.validator.AxleTimeValidator;
import vc.transform.validator.ITimeValidator;

public class TimeParserFactory {

	IParser parser;
	ITimeValidator axleTimeValidator  = new AxleTimeValidator();;
	
	public IParser getParser(Direction direction) throws InAppropriateReadingException{
		switch(direction){
			case NORTH:
				parser = new NorthBoundEntriesTimeParser(axleTimeValidator);
				break;
			case SOUTH:
				parser = new SouthBoundEntriesTimeParser(axleTimeValidator);
				break;
		}
		return parser;
	}

}
