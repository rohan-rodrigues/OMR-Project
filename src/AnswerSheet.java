import java.util.ArrayList;

/***
 * A class to represent a set of answers from a page
 */
public class AnswerSheet {
	private ArrayList<String> pageAnswers;
	private int score;
	
	public AnswerSheet() {
		this.pageAnswers = new ArrayList<>();
		score = 0;
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
	
	public void setScore(ArrayList<String> answers){
		for(int i = 0; i < answers.size(); i++){
			if(answers.get(i).equals(pageAnswers.get(i))){
				score++;
			}
		}
	}
	

}
