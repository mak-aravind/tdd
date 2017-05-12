package vc.app.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import vc.common.dto.VehicleEntry;
import vc.common.exception.InValidIntervalException;
import vc.common.util.FileUtil;
import vc.io.reader.InputReader;
import vc.report.customiser.DefaultReportCustomiser;
import vc.report.customiser.IReportCustomiser;
import vc.report.generator.VehicleEntryReportGenerator;
import vc.transform.classifier.DirectionClassifier;
import vc.transform.classifier.IClassifier;
import vc.transform.core.SensorDataTransMutator;
import vc.transform.factory.TimeParserFactory;

public class VechicleCounterApplication {

	public static void main(String[] args) {
		VechicleCounterApplication app = new VechicleCounterApplication();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
	        	String fileName;
	        	int interval=-1;
	        	System.out.println("Leave empty to pick DEFAULT file.");
	        	System.out.println("Key in file name ALONE if file available in project's src/main/resources folder.");
	        	System.out.println("Otherwise FULLY qualified name.\n\n");
	        	
	        	System.out.print("Enter File Name:");
				fileName = br.readLine();
				System.out.print("Enter Interval - in minutes to create sessions[0-Default configured Intervals]:");
				try{
		            interval = Integer.parseInt(br.readLine());
		        }catch(NumberFormatException nfe){
		            System.err.println("Invalid Format!");
		        }
				app.run(fileName, interval);
			} catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	SensorDataTransMutator sensorDataTransMutator;
	VehicleEntryReportGenerator vehicleEntryReportGenerator;
	InputReader fileReader;
	public VechicleCounterApplication() {
		TimeParserFactory sensorDataParser = new TimeParserFactory();
		IClassifier directionClassifier = new DirectionClassifier();
		sensorDataTransMutator = new SensorDataTransMutator(sensorDataParser, directionClassifier);
		IReportCustomiser defaultReportCustomiser= new DefaultReportCustomiser();
		vehicleEntryReportGenerator = new VehicleEntryReportGenerator(defaultReportCustomiser);
	}
	public void run(String fileName, int userInputInterval) {

		Reader inputReader = null;
		List<String> parsedLines = null;
		List<VehicleEntry> vehicalEntries = null;
		
		try {
			if (null == fileName || fileName.isEmpty()){ 
				fileName = vc.common.constants.File.FILE_NAME;
			}
			inputReader = FileUtil.getInputStreamReader(fileName);
			
		} catch (FileNotFoundException e) {
			try {
				inputReader = new FileReader(fileName);
			} catch (FileNotFoundException fileNotFoundException) {
				System.out.println("File not found Please rerun with valid file name.");
				return;
			}
		}
		if (null==inputReader) return;

		fileReader = new InputReader(inputReader);
		parsedLines = fileReader.getParsedLines();
		if (null == parsedLines || parsedLines.isEmpty()) {
			System.out.println("Invalid/corrupted readings re-run with valid file.");
			return;
		}

		vehicalEntries = sensorDataTransMutator.getVehicalEntries(parsedLines);
		if (null == vehicalEntries || vehicalEntries.isEmpty()) {
			System.out.println("In Appropriate Reading. File has unexpected reading");
			return;
		}
		
		vehicleEntryReportGenerator.setVehicleEntries(vehicalEntries);
		try {
			vehicleEntryReportGenerator.printReport(userInputInterval, false);
		} catch (InValidIntervalException e) {
			System.out.println("Invalid Interval. Cannot distribute the day evenly by interval:"+userInputInterval);
		}
	}
}
