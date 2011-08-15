/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.transform;

import java.awt.image.BufferedImage;

/**
 * Transformer which moves the contents of a horizontal row by a distance in pixels based on the row number
 * @author alan
 *
 */
public class YBasedDisplaceTransformer implements ImageTransformer {
	protected boolean flipAxis = false;
	
	
	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#transform(java.awt.image.BufferedImage)
	 */
	@Override
	public void transform(BufferedImage imageToChange) {
		int height = imageToChange.getHeight();
		int width = imageToChange.getWidth();
		if(this.flipAxis) { // swap x and y
			int tmp = height;
			height = width;
			width = tmp;
		}
		for(int y = 0; y < height; y += 1) {
			int[] buffer = new int[width];
			for(int x=0; x < width; x++) {
				int sourceX = getSourceXForTransform(width, y, x);
				//int rgbLeft = imageToChange.getRGB(x, y);
				int rgbRight = 0;
				if(!this.flipAxis) {
					rgbRight = imageToChange.getRGB(sourceX, y);					
				} else {
					rgbRight = imageToChange.getRGB(y, sourceX);										
				}
				buffer[x] = rgbRight;
			}
			for(int x=0; x < width; x++) {
				if(!this.flipAxis) {
					imageToChange.setRGB(x, y, buffer[x]);					
				} else {
					imageToChange.setRGB(y, x, buffer[x]);
				}
			}
		}
	}

	/**
	 * Calculate the x coordinate of the source pixel for the forward transition
	 * @param width
	 * @param y
	 * @param x
	 * @return
	 */
	protected int getSourceXForTransform(int width, int y, int x) {
		int sourceX = (x + y)%width;
		return sourceX;
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#untransform(java.awt.image.BufferedImage)
	 */
	@Override
	public void untransform(BufferedImage imageToChange) {
		int height = imageToChange.getHeight();
		int width = imageToChange.getWidth();
		if(this.flipAxis) { // swap x and y
			int tmp = height;
			height = width;
			width = tmp;
		}
		for(int y = 0; y < height; y += 1) {
			int[] buffer = new int[width];
			for(int x=0; x < width; x++) {
				int sourceX = getSourceXForReverse(width, y, x);
//				try {
				int rgbLeft = 0;
				if(!this.flipAxis) {
					rgbLeft = imageToChange.getRGB(sourceX, y);					
				} else {
					rgbLeft = imageToChange.getRGB(y, sourceX);
				}
				buffer[x] = rgbLeft;
//				}catch(RuntimeException e) {
//					System.out.println("ox=" + oppositeX + " x=" + x + " y=" + y + " width=" + width);
//					throw e;
//				}
			}
			for(int x=0; x < width; x++) {
				if(!this.flipAxis) {
					imageToChange.setRGB(x, y, buffer[x]);					
				} else {
					imageToChange.setRGB(y, x, buffer[x]);
				}
			}
		}
	}

	/**
	 * Calculate the x coordinate of the source pixel for the inverse transition
	 * @param width
	 * @param y
	 * @param x
	 * @return
	 */
	protected int getSourceXForReverse(int width, int y, int x) {
		int sourceX = x - y%width;
		if(sourceX < 0) {
			sourceX = width + sourceX;
		}
		return sourceX;
	}

	/**
	 * @param flipAxis the flipAxis to set
	 */
	protected void setFlipAxis(boolean flipAxis) {
		this.flipAxis = flipAxis;
	}
	
	

}
