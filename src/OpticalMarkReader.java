import java.util.Arrays;

import processing.core.PImage;

/***
 * Class to perform image processing for optical mark reading
 * 
 */
public class OpticalMarkReader {
	public static OpticalMarkReader markReader = new OpticalMarkReader();
	private AnswerSheet answers = new AnswerSheet();

	private int upperLeftPixelX = 107;
	private int upperLeftPixelY = 464;
	private int rightPixelX = 311;
	private int rightSideOfBubble = 143;
	private int leftSideOfBubble = 126;
	private int leftSideOfBubbleTop = 457;
	private int leftSideOfBubbleBottom = 498;

	private int middleTwoPixels1 = 4;
	private int middleTwoPixels2 = 7;

	/***
	 * Method to do optical mark reading on page image. Return an AnswerSheet
	 * object representing the page answers.
	 * 
	 * @param image
	 * @return
	 */
	public AnswerSheet processPageImage(PImage image) {
		image.filter(image.GRAY);

		PImage answerKey = Main.getAnswerKey();
		int rectangleWidth = rightPixelX - upperLeftPixelX;
		int gapBetweenBubbles = rightSideOfBubble - leftSideOfBubble;
		int rowHeight = leftSideOfBubbleBottom - leftSideOfBubbleTop;
		int rectangleHeight = rowHeight * 25;
	/*	System.out.println("Rectangle Width: " + rectangleWidth);
		System.out.println("Gap Between Bubbles: " + gapBetweenBubbles);
		System.out.println("Row Height: " + rowHeight);
		System.out.println("Rectangle Height: " + rectangleHeight); */
	//	System.out.println("IMAGE HEIGHT: " + image.height + ", Current Height: " + rectangleHeight);
		
	//	System.out.println("Upper left Pixel Y " + upperLeftPixelY);
		
		/*int[][] newPixels = new int[image.height][image.width];
		for (int i = 0; i < image.pixels.length; i++) {
			newPixels[]
		} */
		
		image.loadPixels();
	
		
		for (int j = upperLeftPixelY; j < upperLeftPixelY + rectangleHeight - rowHeight; j = j + rowHeight) {
		//	System.out.println("MAX HEIGHT: " + (upperLeftPixelY + rectangleHeight - rowHeight - upperLeftPixelY));
			for (int i = leftSideOfBubble; i < leftSideOfBubble + rectangleWidth - gapBetweenBubbles; i = i + gapBetweenBubbles) {
				int answer = determineBubble(j,i, rectangleWidth, rectangleHeight, 5, image);
			//	System.out.println(answer);
				if (answer == 1)
					answers.addPage("A");
				if (answer == 2)
					answers.addPage("B");
				if (answer == 3)
					answers.addPage("C");
				if (answer == 4)
					answers.addPage("D");
				if (answer == 5)
					answers.addPage("E");
			}
		}
		System.out.println(answers.getPageAnswers().toString());
		return answers;
	}

	public int determineBubble(int r, int c, int width, int height, int numBubbles, PImage image) {
		int boxWidth = width / numBubbles;
		int max = 255;
		int maxBubble = 0;

		for (int i = 0; i < numBubbles; i++) {

			int value = getSumValue(r, c + i * boxWidth, width, height, image);
			if (value < max) {
				System.out.println("Value: " + value);
				max = value;
				maxBubble = i;
				System.out.println("NEW MAX: " + maxBubble);
			}
		}
		//System.out.println("Final Max: " + max);
		return maxBubble;
	}

	public int getSumValue(int r, int c, int width, int height, PImage image) {
		int sum = 0;
	//	System.out.println("ARRAY LENGTH: " + image.pixels.length);
		for (int i = 0; i < height-r; i++) {
			for (int j = 0; j < width-c; j++) {
				sum += getPixelAt(r + i, c + j, image);
			/*	int val = getPixelAt(r + i, c + j, image);
				System.out.println("VALUE: " + val); */
			}
		}
		
		return sum;
	}

	public int getPixelAt(int row, int col, PImage image) {
		image.loadPixels();

		int index = row * image.width + col;

		int value = image.pixels[index] & 255;
		
		return value;
	}
	
	public static void print2dArr(int[][] arr) {
		for (int row = 0; row < arr.length; row++) {
			for (int col = 0; col < arr[0].length; col++) {
				System.out.print(arr[row][col] + "  ");
			}
			System.out.println();
		}
	}

}
