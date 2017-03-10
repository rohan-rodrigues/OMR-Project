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
		if (problemNumber > 100) {
			return 0;
		}
		
		int numWrong = 0;
		
		for (int i = 0; i < answerSheets.size(); i++) {
			if (!answerSheets.get(i).wasCorrectForProblem(problemNumber, answerKey.getPageAnswers().get(problemNumber-1))) {
				numWrong++;
			}
		}
		
		return numWrong;
	}
	

}
