import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CSVData {
	private double[][] data;
	private String[] columnNames;
	private String[] rowNames;
	private static String filePathToCSV;

	public CSVData(String[] rowNames, String[] columnNames, double[][] data) {
		this.rowNames = rowNames;
		this.data = data;
		this.columnNames = columnNames;
	}
	
	
	/***
	 * Reads file at filepath and returns a string output
	 * @param filepath - the file input
	 * @return a string output
	 */
	private static String readFileAsString(String filepath) {
		StringBuilder output = new StringBuilder();
		try (Scanner scanner = new Scanner(new File(filepath))) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				output.append(line + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	
	/***
	 * This method changes the time stamp of the data to make it start from 0.
	 * @param linesToIgnore
	 */
	private void changeTimeStamp() {
		double value = data[0][0];
		if (value == 0) return;

		data[0][0] = 0;
		for (int i = 1; i < data.length; i++) {
			data[i][0] -= value;
		}
	}
	
	/***
	 * This method returns the object's CSV data.
	 * @return the object's CSV data.
	 */
	public double[][] getData() {
		return this.data;
	}

	/***
	 * returns the individual row out of the array
	 * 
	 * @param rowIndex
	 *            index of the row
	 * @return the array of the rows
	 */
	public double[] getRow(int rowIndex) {
		return data[rowIndex];
	}

	/***
	 * returns the individual col out of the array
	 * 
	 * @param columnIndex
	 *            index of the col
	 * @return returns the array of the columns
	 */
	public double[] getColumn(int columnIndex) {
		double[] column = new double[data.length];
		for (int j = 0; j < data.length; j++)
			column[j] = data[j][columnIndex];

		return column;
	}

	/***
	 * returns the individual col out of the array
	 * 
	 * @param name
	 *            the name of the col
	 * @return returns the name of the columns
	 */
	public double[] getColumn(String name) {
		int columnIndex = indexOf(columnNames, name);
		return getColumn(columnIndex);
	}

	/***
	 * returns multiple rows out of the data array
	 * 
	 * @param startIndex
	 *            the index you are starting at
	 * @param endIndex
	 *            the index you are ending at
	 * @return returns the array of the rows
	 */
	public double[][] getRows(int startIndex, int endIndex) {
		double[][] rows = new double[endIndex - startIndex + 1][data[0].length];
		
		for (int i = startIndex; i <= endIndex; i++)
			rows[i] = getRow(i);
		return rows;
	}

	/***
	 * returns multiple rows out of the data array
	 * 
	 * @param rowIndexes
	 *            the array of the indexes of the row
	 * @return returns the array of the rows
	 */
	public double[][] getRows(int[] rowIndexes) {
		double[][] rows = new double[rowIndexes.length][data[0].length];
		
		for (int i = 0; i < rowIndexes.length; i++) 
			rows[i] = getRow(rowIndexes[i]);
		return rows;
	}

	/***
	 * returns the multiple columns out of the data array
	 * 
	 * @param columnIndexes
	 *            the array of the indexes of the columns
	 * @return returns the array of the columns
	 */

	public double[][] getColumns(int[] columnIndexes) {
		double[][] cols = new double[data.length][columnIndexes.length];

		for (int i = 0; i < columnIndexes.length; i++) {
			double[] col = getColumn(columnIndexes[i]);
			for(int j = 0; j < col.length; j++)
				cols[j][i] = col[j];
		}
		return cols;
	}

	/***
	 * returns the multiple rows out of the data array
	 * 
	 * @param startIndex
	 *            the index you are starting at
	 * @param endIndex
	 *            the index you are ending at
	 * @return returns the array of the columns
	 */
	public double[][] getColumns(int startIndex, int endIndex) {
		double[][] cols = new double[data.length][endIndex - startIndex + 1];
		for (int i = startIndex; i <= endIndex; i++) {
			double[] col = getColumn(i);
			for(int j = 0; j < col.length; j++)
				cols[j][i-startIndex] = col[j];
		}
		return cols;
	}

	/***
	 * returns the multiple columns out of the data array
	 *
	 * @param colNames
	 *            the name of the column
	 * @return returns the array of the columns
	 */
	public double[][] getColumns(String[] colNames) {
		int[] colIndexes = new int[colNames.length];
		for(int i = 0; i < colNames.length; i++)
			colIndexes[i] = indexOf(columnNames, colNames[i]);
		return getColumns(colIndexes);
	}
	
	public double[][] getAccelColumns(){
		int[] indexes = new int[3];
		for(int i = 0; i < columnNames.length; i++){
			if(columnNames[i].toLowerCase().contains("accel")){
				if(columnNames[i].toLowerCase().contains("x")) indexes[0] = i;
				else if(columnNames[i].toLowerCase().contains("y")) indexes[1] = i;
				else if(columnNames[i].toLowerCase().contains("z")) indexes[2] = i;
			}
		}
		return getColumns(indexes);
	}

	/***
	 * sets the value of the the element in the array
	 * 
	 * @param rowIndex
	 *            the index of the row
	 * @param colIndex
	 *            the index of the column
	 * @param newValue
	 *            the value you want to set the element to
	 */
	public void setValue(int rowIndex, int colIndex, double newValue) {
		data[rowIndex][colIndex] = newValue;
	}

	/***
	 * sets the values of the rows
	 * 
	 * @param values
	 *            the array of the row values
	 */
	public void setRow(int row, double[] values) {
		for(int i = 0; i < values.length; i++)
			data[row][i] = values[i];
	}

	/***
	 * sets the values of the columns
	 * 
	 * @param values
	 *            the array of the col values
	 */
	public void setColumn(int column, double[] values) {
		for(int i = 0; i < values.length; i++)
			data[i][column] = values[i];
	}

	/***
	 * gets the names of the columns
	 * 
	 * @return returns the array of the column names
	 */
	public String[] getColumnTitles() {
		return columnNames;
	}
	
	/***
	 * Returns the index of the input String in the input array
	 * @param arr - the array that is used to check the index of the input n
	 * @param n - the String that is searched for
	 * @return the index of the input String in the input array
	 */
	public int indexOf(String[] arr, String n){
		for(int i = 0; i < arr.length; i++)
			if(arr[i].equals(n)) return i;
		return -1;
	}
	
	
	/***
	 * Converts a 2D array to a String
	 * @param arr - the array that is converted into a String
	 * @return a string that is converted from a 2D array
	 */
	public String Array2DtoString(double[][] arr) {
		String str = Array1DtoString(columnNames);
		for (int i = 1; i < arr.length; i++) {
			str += "\n" + rowNames[i] + "," + Array1DtoString(arr[i]);
		}
		return str;
	}
	
	
	/***
	 * Converts a 1D double array to a String
	 * @param arr - the array that is converted into a String
	 * @return a string that is converted from a 1D array
	 */
	public String Array1DtoString(double[] arr) {
		String str = "" + arr[0];
		for (int i = 1; i < arr.length; i++) {
			str += "," + arr[i];
		}
		return str;
	}
	
	/***
	 * Converts a 1D String array to a String
	 * @param arr - the array that is converted into a String
	 * @return a string that is converted from a 1D array
	 */
	public String Array1DtoString(String[] arr) {
		String str = "" + arr[0];
		for (int i = 1; i < arr.length; i++) {
			str += "," + arr[i];
		}
		return str;
	}

	/***
	 * saves the current state of the object back into a CSV
	 * 
	 * @param filename
	 *            the name of the file
	 * @throws IOException 
	 */
	public void saveToFile(String filename) throws IOException {
		String str = Array2DtoString(data);
		
		File file = new File(filename);
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(str);
		bw.close();
	}
	
	
}





/*import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVData {
	private final String FILE_PATH_PERCENT;
	private final String FILE_PATH_ANALYSIS;
	private ManageAnswersSheets pageAnswers;
	private final String[] columnNames = {"Percent Right"};
	
	public CSVData(String filepath1, String filepath2, ManageAnswersSheets pageAnswers) {
		this.pageAnswers = pageAnswers;
		this.FILE_PATH_PERCENT = filepath1;
		this.FILE_PATH_ANALYSIS = filepath2;
	}
	
	public void writeToCSVPercent() throws IOException {
		String str = convertToString(pageAnswers.getAnswerSheetList());
		File file = new File(FILE_PATH_PERCENT);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(str);
		bw.close();
	}
	
	public void writeToCSVAnalysis() throws IOException {
		String str = getItemAnalysis();
		File file = new File(FILE_PATH_ANALYSIS);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(str);
		bw.close();
	}

	private String convertToString(ArrayList<AnswerSheet> list) {
		String str = "";
		str += columnNames[0] + System.lineSeparator();
		for (int i = 0; i < list.size(); i++) {
			str += list.get(i).percentRight();
			str += System.lineSeparator();
		}
		return str;
	}
	
	private String getItemAnalysis() {
		String str = "";
		for (int i = 1; i <= 100; i++) {
			str += pageAnswers.wrongForProblem(i);
			str += System.lineSeparator();
		}
		return str;
	}

} */
