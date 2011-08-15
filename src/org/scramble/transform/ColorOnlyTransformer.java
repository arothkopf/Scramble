/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.transform;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.scramble.util.color.ColorTransformer;


/**
 * Pixel transformer that changes colors in place
 * @author alan
 *
 */
public class ColorOnlyTransformer implements ImageTransformer {
	ColorTransformer pixelTransformer;
	
	
	/**
	 * CTOR
	 * @param pixelTransformer
	 */
	public ColorOnlyTransformer(ColorTransformer pixelTransformer) {
		super();
		this.pixelTransformer = pixelTransformer;
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#transform(java.awt.image.BufferedImage)
	 */
	@Override
	public void transform(BufferedImage imageToChange) {
		int height = imageToChange.getHeight();
		int width = imageToChange.getWidth();
		for(int y = 0; y < height; y++) {
			for(int x=0; x < width; x++) {
			
				int rgb = imageToChange.getRGB(x, y);
//				if(x + y == 0) { // TODO : delete
//					Color tmpColor = new Color(rgb);
//					System.out.println("before: " + rgb + " - " + tmpColor + " - " + tmpColor.getAlpha()+ " - " + tmpColor.getTransparency());
//				}	
				modifyPixel(imageToChange, y, x, rgb);
				
//				if(x + y == 0) { // TODO : delete
//					Color tmpColor = new Color(imageToChange.getRGB(x, y));
//					System.out.println("after: " + imageToChange.getRGB(x, y) + " - " + tmpColor + " - " + tmpColor.getAlpha()+ " - " + tmpColor.getTransparency());
//				}	
			}
		}
	}

	/**
	 * Pixel color change for forward transformation
	 * @param imageToChange
	 * @param y
	 * @param x
	 * @param rgb
	 */
	protected void modifyPixel(BufferedImage imageToChange, int y, int x,
			int rgb) {
		imageToChange.setRGB(x, y, this.pixelTransformer.transform(rgb));
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#untransform(java.awt.image.BufferedImage)
	 */
	@Override
	public void untransform(BufferedImage imageToChange) {
		int height = imageToChange.getHeight();
		int width = imageToChange.getWidth();
		for(int y = 0; y < height; y++) {
			for(int x=0; x < width; x++) {
				int rgb = imageToChange.getRGB(x, y);
				unmodifyPixel(imageToChange, y, x, rgb);
			}
		}
	}

	/**
	 * Pixel color change for reverse transformation
	 * @param imageToChange
	 * @param y
	 * @param x
	 * @param rgb
	 */
	protected void unmodifyPixel(BufferedImage imageToChange, int y, int x,
			int rgb) {
		imageToChange.setRGB(x, y, this.pixelTransformer.reverseTransformation(rgb));
	}
}
