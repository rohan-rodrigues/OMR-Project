import java.util.ArrayList;

/***
 * A class to represent a set of answers from a page
 */
public class AnswerSheet {
	private ArrayList<String> pageAnswers;
	private int score = 0;
	private int numProblems = 25;
	
	public AnswerSheet() {
		pageAnswers = new ArrayList<>();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public ArrayList<String> getPageAnswers() {
		return pageAnswers;
	}

	public void setPageAnswers(ArrayList<String> pageAnswers) {
		this.pageAnswers = pageAnswers;
	}
	
	public void addPage(String s) {
		pageAnswers.add(s);
	}
	
	public void removePage(String s) {
		for (int i = 0; i < pageAnswers.size(); i++) {
			if (pageAnswers.get(i).equals(s)) {
				pageAnswers.remove(i);
			}
		}
	}
	
	public void checkScoreWithKey(ArrayList<String> answers){
		for (int i = 0; i < answers.size(); i++) {
			if (answers.get(i).equals(pageAnswers.get(i))){
				score++;
			}
		}
	}
	
	public void printAnswers() {
		System.out.println(pageAnswers.toString());
	}
	
	public int percentRight() {
		return score / numProblems;
	}
	
	
	public boolean wasCorrectForProblem(int number, String correctAnswer) {
		return (pageAnswers.get(number).equals(correctAnswer));
	}

}
