package vc.transform.parser;

import java.util.List;

import vc.common.exception.InAppropriateReadingException;

public interface IParser {
	public List<Integer> parse(final List<String> fromSensorData) throws InAppropriateReadingException;
}
