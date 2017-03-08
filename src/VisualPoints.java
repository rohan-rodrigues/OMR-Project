import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class VisualPoints extends PApplet {
	ArrayList<PImage> images;
	PImage current_image;
	int currentImageIndex = 0;
	int w = 1200;
	int h = 900;
	int leftSideOfFirstCol = -1, rightSideOfFirstCol = -1;
	int leftSideOfSecondCol = -1, rightSideOfSecondCol = -1;
	int leftSideOfThirdCol = -1, rightSideOfThirdCol = -1;
	int leftSideOfFourthCol = -1, rightSideOfFourthCol = -1;
	int topOfCol = -1, bottomOfCol = -1;
	int leftSideOfBubble = -1, rightSideOfBubble = -1, topOfBubble = -1, bottomOfBubble = -1;
	boolean allValuesFound = false;
	int index = 0;
	

	public void setup() {
		size(w, h);
		images = PDFHelper.getPImagesFromPdf("/omrtest.pdf");
		
		JOptionPane pane = new JOptionPane();
		pane.createDialog("Click on the left side of the first column");
		pane.createDialog("Click on the right side of the first column");
		pane.createDialog("Click on the left side of the second column");
		pane.createDialog("Click on the right side of the second column");
		pane.createDialog("Click on the left side of the third column");
		pane.createDialog("Click on the right side of the third column");
		pane.createDialog("Click on the left side of the fourth column");
		pane.createDialog("Click on the right side of the fourth column");
		pane.createDialog("Click on the top of the first column");
		pane.createDialog("Click on the bottom of the first column");
		pane.createDialog("Click on the left side of the first bubble");
		pane.createDialog("Click on the right side of the first bubble");
		pane.createDialog("Click on the top of the first bubble");
		pane.createDialog("Click on the bottom of the first bubble");
	}

	public void draw() {
		background(255);
		if (images.size() > 0) {
			current_image = images.get(currentImageIndex);
			image(current_image, 0, 0);			// display image i
			fill(0);
			text(mouseX + " " + mouseY, 30, 30);
		}
	}

	public void mouseReleased() {
		currentImageIndex = (currentImageIndex + 1) % images.size();			// increment current image
	}
	
	public void mousePressed() {
		switch(index) {
			case 0:
				leftSideOfFirstCol = mouseX;
				break;
			case 1:
				rightSideOfFirstCol = mouseX;
				break;
			case 2:
				leftSideOfSecondCol = mouseX;
				break;
			case 3:
				rightSideOfSecondCol = mouseX;
				break;
			case 4:
				leftSideOfThirdCol = mouseX;
				break;
			case 5:
				rightSideOfThirdCol = mouseX;
				break;
			case 6:
				leftSideOfFourthCol = mouseX;
				break;
			case 7:
				rightSideOfFourthCol = mouseX;
				break;
			case 8:
				topOfCol = mouseY;
				break;
			case 9:
				bottomOfCol = mouseY;
				break;
			case 10:
				leftSideOfBubble = mouseX;
				break;
			case 11:
				rightSideOfBubble = mouseX;
				break;
			case 12:
				topOfBubble = mouseY;
				break;
			case 13:
				bottomOfBubble = mouseY;
				break;
		}
	}
	
	
	
}
