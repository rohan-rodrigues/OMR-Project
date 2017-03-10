import java.io.IOException;
import java.util.ArrayList;

import processing.core.PImage;

public class Main {
	public static final String PDF_PATH = "/omrtest.pdf";
	public static OpticalMarkReader markReader = new OpticalMarkReader();

	public static void main(String[] args) throws IOException {
		System.out.println("Welcome!  I will now auto-score your pdf!");
		
		/*VisualPoints tester = new VisualPoints();
		PApplet.runSketch(new String[] {" Tester " }, tester); */
		
		
		System.out.println("Loading file..." + PDF_PATH);
		ArrayList<PImage> images = PDFHelper.getPImagesFromPdf(PDF_PATH);

		System.out.println("Scoring all pages...");
		scoreAllPages(images);

		System.out.println("Complete!");

		// Optional: add a saveResults() method to save answers to a csv file
	}

	/***
	 * Score all pages in list, using index 0 as the key.
	 * 
	 * NOTE: YOU MAY CHANGE THE RETURN TYPE SO YOU RETURN SOMETHING IF YOU'D
	 * LIKE
	 * 
	 * @param images
	 *            List of images corresponding to each page of original pdf
	 * @throws IOException 
	 */
	private static void scoreAllPages(ArrayList<PImage> images) throws IOException {
		ArrayList<AnswerSheet> scoredSheets = new ArrayList<AnswerSheet>();

		// Score the first page as the key
		AnswerSheet key = markReader.processPageImage(images.get(0));

		System.out.println(key.getPageAnswers().toString());
		for (int i = 1; i < images.size(); i++) {
			PImage image = images.get(i);

			AnswerSheet answers = markReader.processPageImage(image);
			
			System.out.println(answers.getPageAnswers().toString());
			
			answers.checkScoreWithKey(key.getPageAnswers());
			
			scoredSheets.add(answers);
		
		//	System.out.println(answers.getScore());
		}
		
		ManageAnswersSheets ms = new ManageAnswersSheets();
		ms.setAnswerSheetList(scoredSheets);
		ms.addAnswerKey(key);
				
		CSVData data1 = new CSVData(createRowNamesForPercent(ms.getAnswerSheetList()), createColumnNamesForPercent(ms.getAnswerSheetList()), ms.getScores2DArr());
		data1.saveToFile("/Users/rohanrodrigues/Documents/file1.csv");
		
		CSVData data2 = new CSVData(createRowNamesForAnalysis(ms.getAnswerSheetList()), createColumnNamesForAnalysis(ms.getAnswerSheetList()), ms.getItemsAnalysis());
		data2.saveToFile("/Users/rohanrodrigues/Documents/file2.csv");
	}

	private static String[] createRowNamesForPercent(ArrayList<AnswerSheet> answerSheetList) {
		String[] names = new String[answerSheetList.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = "Page: " + i;
		}
		return names;
	}

	private static String[] createColumnNamesForPercent(ArrayList<AnswerSheet> answerSheetList) {
		return new String[] {"Page #", "Score"};
	}
	
	private static String[] createRowNamesForAnalysis(ArrayList<AnswerSheet> answerSheetList) {
		String[] names = new String[answerSheetList.get(0).getPageAnswers().size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = "Problem: " + i;
		}
		return names;
	}

	private static String[] createColumnNamesForAnalysis(ArrayList<AnswerSheet> answerSheetList) {
		return new String[] {"Problem #", "Number of Students Who Got It Wrong", "% of Students Got It Wrong"};
	}

	public static PImage getAnswerKey() {
		ArrayList<PImage> images = PDFHelper.getPImagesFromPdf(PDF_PATH);
		return images.get(0);
	}
}