package utils;

import java.awt.image.BufferedImage;

public abstract class ImageParsing {

	
	
	// for upscaling, downscaling not implemented...
	public static BufferedImage upScaleImage(int scaleFactor, BufferedImage img) {
		
		BufferedImage parsedImg = new BufferedImage((img.getWidth() * scaleFactor + scaleFactor),(img.getHeight() * scaleFactor + scaleFactor), BufferedImage.TYPE_INT_RGB);
		
		for(int y = 0; y < parsedImg.getHeight()-scaleFactor; y = y+scaleFactor) {
			for(int x = 0; x < parsedImg.getWidth()-scaleFactor; x = x+scaleFactor) {
				for(int i = 0; i < scaleFactor; i++) {
					for(int j = 0; j < scaleFactor; j++) {
						parsedImg.setRGB(x+i, y+j, img.getRGB(x/scaleFactor, y/scaleFactor));
					}
				}
			}
		}
		return parsedImg;
	}
	
	
}
