package vc.io.reader;


import static vc.common.constants.File.INPUT_VALIDATION_REGEX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class InputReader {
	
	private final Reader input;
	private final Pattern pattern = Pattern.compile(INPUT_VALIDATION_REGEX);
	
	public InputReader(Reader input){
		this.input = input;
	}
	public List<String> getParsedLines(){
		List<String> inputLines = new ArrayList<String>();
		try (BufferedReader reader = new BufferedReader(input)) {
			String line;
            while ((line = reader.readLine()) != null) {
            	if (isValid(line))
                    inputLines.add(line.toUpperCase());
                else
                    return Collections.emptyList();
            }
		} catch (IOException e) {
			return Collections.emptyList();
		}
		return inputLines;
	}
	private boolean isValid(String line){
        return pattern.matcher(line).matches();
    }
}
