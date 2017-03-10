import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVData {
	private final String FILE_PATH_PERCENT;
	private final String FILE_PATH_ANALYSIS;
	private ManageAnswersSheets pageAnswers;
	
	public CSVData(String filepath1, String filepath2, ManageAnswersSheets pageAnswers) {
		this.pageAnswers = pageAnswers;
		this.FILE_PATH_PERCENT = filepath1;
		this.FILE_PATH_ANALYSIS = filepath2;
	}
	
	public void writeToCSVPercent() throws IOException {
		String str = convertToString(pageAnswers.getAnswerSheetList());
		File file = new File(FILE_PATH_PERCENT);
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(str);
		bw.close();
	}
	
	public void writeToCSVAnalysis() throws IOException {
		String str = getItemAnalysis();
		File file = new File(FILE_PATH_ANALYSIS);
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(str);
		bw.close();
	}

	private String convertToString(ArrayList<AnswerSheet> list) {
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			str += list.get(i).percentRight() + "\n";
		}
		return str;
	}
	
	private String getItemAnalysis() {
		String str = "";
		for (int i = 1; i <= 100; i++) {
			str += pageAnswers.wrongForProblem(i) + "\n";
		}
		return str;
	}

}
