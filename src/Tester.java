import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Tester extends PApplet {
	ArrayList<PImage> images;
	PImage current_image;
	int i = 0;
	int w = 1200;
	int h = 900;

	public void setup() {
		size(w, h);
		images = new ArrayList<PImage>();
		getPImagesFromPdf("../assets/omrtest.pdf", images);
	}

	public void draw() {
		background(255);
		if (images.size() > 0) {
			current_image = images.get(i);
			image(current_image, 0, 0);			// display image i
		}
	}

	public void mouseReleased() {
		i = (i + 1) % images.size();			// increment current image
	}
	
	// Loads pdf file from path, converts each page to a PImage and stores them sequentially
	// in the images ArrayList.
	public void getPImagesFromPdf(String path, ArrayList<PImage> images) {
		//InputStream is = this.getClass().getResourceAsStream(path);

		PDDocument pdf = null;
		try {
			pdf = PDDocument.load(path);
		} catch (IOException e) {
			System.out.println("Couldn't load pdf");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		List<PDPage> pages = pdf.getDocumentCatalog().getAllPages();

		for (PDPage page : pages) {
			try {
				BufferedImage image = page.convertToImage();

				PImage img = new PImage(image.getWidth(), image.getHeight(),
						PConstants.ARGB);
				image.getRGB(0, 0, img.width, img.height, img.pixels, 0,
						img.width);
				img.updatePixels();

				images.add(img);
				System.out.println("Adding page " + images.size());
			} catch (IOException e) {
				System.out.println("problem converting to image");
				e.printStackTrace();
			}
		}
		
		try {
			pdf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
