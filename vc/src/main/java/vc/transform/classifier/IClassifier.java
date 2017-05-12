package vc.transform.classifier;

import java.util.List;

public interface IClassifier {
	public List<String> getEntriesToFindDirection(final List<String> fromSensorData);
}
