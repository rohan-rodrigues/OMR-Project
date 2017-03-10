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
	
	private int gapBetweenRectangles = 90;

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

		int rectangleWidth = rightPixelX - upperLeftPixelX;
		int rowHeight = leftSideOfBubbleBottom - leftSideOfBubbleTop;
		int rectangleHeight = rowHeight * 25;
		
		image.loadPixels();
	
		int boxWidth = rectangleWidth / 5;
		int max = 255;
		int maxBubble = 0;
		int answer = 0;
		
		for (int a = upperLeftPixelX; a < 1170 - upperLeftPixelX; a+= gapBetweenRectangles) {
		for (int j = upperLeftPixelY; j < upperLeftPixelY + rectangleHeight - rowHeight; j += rowHeight) {
			int count = 1;
			for (int i = a; i < a + rectangleWidth - rectangleWidth/5; i += rectangleWidth/5) {
				int value = getSumValue(j, i, boxWidth, rowHeight, image);
				if (value < max) {
					max = value;
					maxBubble = count;
				}
				count++;
			}
			count = 1;
			max = 255;
			answer = maxBubble;
			
			if (answer == 1)
				answers.addPage("A");
			else if (answer == 2)
				answers.addPage("B");
			else if (answer == 3)
				answers.addPage("C");
			else if (answer == 4)
				answers.addPage("D");
			else if (answer == 5)
				answers.addPage("E");
		}
		}
		return answers;
	}

	public int getSumValue(int r, int c, int width, int height, PImage image) {
		int sum = 0;
		
		for (int i = r; i < height + r; i++) {
			for (int j = c; j < width + c; j++) {
				sum += getPixelAt(i, j, image);
			}
		}
		return sum / (width * height);
	}

	public int getPixelAt(int row, int col, PImage image) {
		image.loadPixels();

		int index = row * image.width + col;
		int value = image.pixels[index];
		
		return value & 255;
	}

}
