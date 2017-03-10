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
			
			answers.setScoreWithList(key.getPageAnswers());
			
			scoredSheets.add(answers);
		
		//	System.out.println(answers.getScore());
		}
		
		ManageAnswersSheets ms = new ManageAnswersSheets();
		ms.setAnswerSheetList(scoredSheets);
		ms.addAnswerKey(key);
				
		CSVData data = new CSVData("/Users/rohanrodrigues/Documents/file1.csv", "/Users/rohanrodrigues/Documents/file2.csv", ms);
		data.writeToCSVPercent();
		data.writeToCSVAnalysis();
	}

	public static PImage getAnswerKey() {
		ArrayList<PImage> images = PDFHelper.getPImagesFromPdf(PDF_PATH);
		return images.get(0);
	}
}