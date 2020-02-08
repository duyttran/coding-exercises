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
import java.util.List;



/**
 * @author dtran
 *
 */
public class StandingOvation {
	
	public static String runTest(List<String> testCase) {
		String[] test = testCase.get(0).split(" ");
		int maxShyness = Integer.valueOf(test[0]);
		System.out.println("Testing case " + testCase);

		int totalPeople = 0;
		int numToInvite = 0;
		for (int i = 0; i <= maxShyness; i++) {
			int numPeople = Integer.valueOf(String.valueOf(test[1].charAt(i)));
			System.out.println("There are " + numPeople + " people with shyness " + i);
			if (numPeople == 0) {
				continue;
			} else if (totalPeople < i) {
				numToInvite += i - (totalPeople);
				totalPeople += (numToInvite + numPeople);
			} else {
				totalPeople += numPeople;
			}
			System.out.println("Total people clapping " + totalPeople);
		}
		System.out.println("Need to invite " + numToInvite + " people");
		
		return String.valueOf(numToInvite);
	}
	
	public static void main (String [] args) throws IOException {
		
		String packageName = "codejam_2015/qualification";
		String fileNamePrefix = "standingovation";
		FileHelper.Size testSize = FileHelper.Size.big;
		int linesPerTestcase = 1;
		
		FileHelper fileHelper = new FileHelper(packageName, fileNamePrefix, testSize, linesPerTestcase);
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
