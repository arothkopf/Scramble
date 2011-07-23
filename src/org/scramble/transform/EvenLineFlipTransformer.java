/**
 * 
 */
package org.scramble.transform;

import java.awt.image.BufferedImage;

/**
 * Transformer which horizontally flips every other line
 * @author alan
 *
 */
public class EvenLineFlipTransformer implements ImageTransformer {
	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#transform(java.awt.image.BufferedImage)
	 */
	@Override
	public void transform(BufferedImage imageToChange) {
		int height = imageToChange.getHeight();
		int width = imageToChange.getWidth();
		for(int y = 0; y < height; y += 2) {
			for(int x=0; x < width/2; x++) {
				int oppositeX = width - 1 - x;
				int rgbLeft = imageToChange.getRGB(x, y);
				int rgbRight = imageToChange.getRGB(oppositeX, y);
				imageToChange.setRGB(oppositeX, y, rgbLeft);
				imageToChange.setRGB(x, y, rgbRight);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#untransform(java.awt.image.BufferedImage)
	 */
	@Override
	public void untransform(BufferedImage imageToChange) {
		this.transform(imageToChange);
	}

}
