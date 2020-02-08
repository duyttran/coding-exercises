/**
 * 
 */
package template;

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
public class TestTemplate {
	
	public static String runTest(List<String> testCase) {
		return null;
	}
	
	public static void main (String [] args) throws IOException {
		FileHelper fileHelper = new FileHelper("packagename", "testname", FileHelper.Size.small, 1);
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
