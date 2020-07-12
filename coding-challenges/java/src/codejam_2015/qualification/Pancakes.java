/**
 * 
 */
package codejam_2015.qualification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * @author dtran
 *
 */
public class Pancakes {
	
	public static String runTest(List<String> testCase) {
		int plates = Integer.parseInt(testCase.get(0));
		List<String> temp = Arrays.asList(testCase.get(1).split(" "));
		List<Integer> pancakesPerPlate = new ArrayList<Integer>();
		for (String plate : temp) {
			pancakesPerPlate.add(Integer.valueOf(plate));
		}		
		System.out.println("There are " + plates + " plates");
		System.out.println(pancakesPerPlate);
		
		int minutesElapsed = 0;
		while(isStillPancakes(pancakesPerPlate)) {
			int possiblePancakesEaten = countPancakesEaten(pancakesPerPlate);
			int highestPancakeIndex = 0;
			int highestPancakeCount = 0;
			for (int i = 0; i < pancakesPerPlate.size(); i++) {
				if (pancakesPerPlate.get(i) > highestPancakeCount) {
					highestPancakeIndex = i;
					highestPancakeCount = pancakesPerPlate.get(i);
				}
			}
			int givePancakesCount = highestPancakeCount / 2;
			
			//interruption!
			if (givePancakesCount >= possiblePancakesEaten) {
				pancakesPerPlate.set(highestPancakeIndex, highestPancakeCount - givePancakesCount);
				if (pancakesPerPlate.contains(0)) {
					pancakesPerPlate.set(pancakesPerPlate.indexOf(0), givePancakesCount);				
				} else {
					pancakesPerPlate.add(givePancakesCount);
				}				
			} else {				
				eatPancakes(pancakesPerPlate);
			}
			
			
			minutesElapsed++;
			System.out.println(pancakesPerPlate);
			
		}
		System.out.println(minutesElapsed);
		return String.valueOf(minutesElapsed);
	}
	
	public static int countPancakesEaten(List<Integer> pancakesPerPlate) {
		int pancakes = 0;
		for (Integer plate : pancakesPerPlate) {
			if (plate > 0) {
				pancakes++;
			}
		}
		return pancakes;
	}
	public static void eatPancakes(List<Integer> pancakesPerPlate) {
		for (int i = 0; i < pancakesPerPlate.size(); i ++) {
			int numPancakes = pancakesPerPlate.get(i);
			if (numPancakes >= 0) {
				pancakesPerPlate.set(i, numPancakes - 1);				
			}
		}
	}
	
	public static boolean isStillPancakes(List<Integer> pancakesPerPlate) {
		for (Integer plate : pancakesPerPlate) {
			if (plate > 0) {
				return true;
			}
		}
		return false;
	}
	
	
	public static void main (String [] args) throws IOException {
		FileHelper fileHelper = new FileHelper("codejam_2015/qualification", "pancakes", FileHelper.Size.small, 2);
		List<List<String>> testCases = fileHelper.getTestCases();
		
		List<String> results = new ArrayList<String>();
		int testNumber = 1;
		for (List<String> testCase : testCases) {
			results.add("Case #" + testNumber++ + ": " + runTest(testCase));
		}
		fileHelper.writeOutput(results);
	}
	
	/*
	 * --------------------------------------------------------------------------------------
	 * HELPER CLASS
	 * --------------------------------------------------------------------------------------
	 */
	private static class FileHelper {
		String inputFileName;
		String outputFileName;
		int testCaseLineLength;
		public enum Size {
			big,
			small;
		}
		
		public FileHelper(String packageName, String problemName, Size testSize, int testCaseLineLength) {
			this.inputFileName = "src/" + packageName + "/" + problemName + "-" + testSize + "-input.txt";
			this.outputFileName = "src/" + packageName + "/" + problemName + "-" + testSize + "-output.txt";
			this.testCaseLineLength = testCaseLineLength;
		}

		private List<String> readInput() throws IOException {
			List<String> inputLines = new ArrayList<String>(); 
			File file = new File(inputFileName);
			BufferedReader in = new BufferedReader(new FileReader(file));
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				inputLines.add(line);
			}
			in.close();
			return inputLines;
		}
		
		public List<List<String>> getTestCases() throws IOException {
			
			List<String> input = readInput();
			Integer countTestCases = Integer.valueOf(input.get(0));
			input.remove(0);
			List<List<String>> testCases = new ArrayList<List<String>>();
			
			for (int i = 0; i < countTestCases; i++) {
				List<String> testCase = new ArrayList<String>();
				for (int j = 0; j < this.testCaseLineLength; j++) {
					testCase.add(input.get(0));
					input.remove(0);
				}
				testCases.add(testCase);
			}
			
			return testCases;
		}
		
		public void writeOutput(List<String> testCaseResults) throws IOException {
			File file = new File(outputFileName);
			file.createNewFile();
			FileOutputStream fileOutput = new FileOutputStream(outputFileName);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutput));
			for(String result : testCaseResults) {
				writer.write(result);		
				writer.newLine();
			}
			writer.close();
			
		}
	}
}
