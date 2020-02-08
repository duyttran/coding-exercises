/**
 * 
 */
package codejam_2014.qualification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author dtran
 *
 */
public class MagicTrick {
	
	public static String runTest(List<String> testCase) {

		Integer row = Integer.valueOf(testCase.get(0));
		
		String[] values = testCase.get(row).split(" ");
		for(int i = 0; i < 5; i++) {
			testCase.remove(0);			
		}

		row = Integer.valueOf(testCase.get(0));
		String[] values2 = testCase.get(row).split(" ");
		for(int i = 0; i < 5; i++) {
			testCase.remove(0);			
		}
		
		return compareIntersection(values, values2);
	}
	
	public static String compareIntersection(String[] values, String[] values2) {
		Set<String> s1 = new HashSet<String>(Arrays.asList(values));
		Set<String> s2 = new HashSet<String>(Arrays.asList(values2));
		s1.retainAll(s2);
		if (s1.isEmpty()) {
			return "Volunteer cheated!";
		} else if (s1.size() == 1) {
			return (String) s1.toArray()[0];
		} else {
			return "Bad magician!";
		}
		
	}
	
	public static void main (String [] args) throws IOException {
		FileHelper fileHelper = new FileHelper("qualification", "magictrick", FileHelper.Size.small, 10);
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
