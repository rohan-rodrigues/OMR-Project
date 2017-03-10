import java.util.ArrayList;

public class ManageAnswersSheets {
	private ArrayList<AnswerSheet> answerSheets;
	private AnswerSheet answerKey;
	
	public ManageAnswersSheets() {
		this.answerSheets = new ArrayList<>();
	}
	
	public void addAnswerKey(AnswerSheet answerKey) {
		this.answerKey = answerKey;
	}
	
	public void addAnswerSheet(AnswerSheet a) {
		answerSheets.add(a);
	}
	
	public void removeAnswer(AnswerSheet a) {
		for (int i = 0; i < answerSheets.size(); i++) {
			if (answerSheets.get(i).equals(a)) {
				answerSheets.remove(i);
			}
		}
	}
	
	public ArrayList<AnswerSheet> getAnswerSheetList() {
		return this.answerSheets;
	}
	
	public void setAnswerSheetList(ArrayList<AnswerSheet> answers) {
		this.answerSheets = answers;
	}
	
	public int wrongForProblem(int problemNumber) {
		if (problemNumber > 25) {
			return 0;
		}
		
		int numWrong = 0;
		
		for (int i = 0; i < answerSheets.size(); i++) {
			if (!answerSheets.get(i).wasCorrectForProblem(problemNumber, answerKey.getPageAnswers().get(problemNumber))) {
				numWrong++;
			}
		}
		return numWrong;
	}
	
	public double[][] getScores2DArr() {
		double[][] arr = new double[answerSheets.size()][1];
		
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr[i][j] = answerSheets.get(i).getScore();
			}
		}
		
		return arr;
	}
	
	public double[][] getItemsAnalysis() {
		double[][] arr = new double[answerSheets.size()][2];
		
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				int wrong = wrongForProblem(i);
				if (j == 0)
					arr[i][j] = wrong;
				else 
					arr[i][j] = (100 * wrong)/answerSheets.size();
			}
		}
		return arr;
	}
	
	public static void print2dArr(double[][] arr) {
		for (int row = 0; row < arr.length; row++) {
			for (int col = 0; col < arr[0].length; col++) {
				System.out.print(arr[row][col] + "  ");
			}
			System.out.println();
		}
	}
	

}
