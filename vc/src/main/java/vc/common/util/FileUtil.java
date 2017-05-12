package vc.common.util;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;


public class FileUtil {
	
	public static Reader getInputStreamReader(String fileName) throws FileNotFoundException{
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader.getResourceAsStream(fileName) == null)
			throw new FileNotFoundException();
		return new InputStreamReader(classLoader.getResourceAsStream(fileName));
	}
}
