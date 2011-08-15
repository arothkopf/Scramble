/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.transform;

import java.awt.image.BufferedImage;

/**
 * Vertical flip of every other column
 * @author alan
 *
 */
public class EvenColumnFlipTransformer implements ImageTransformer {

	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#transform(java.awt.image.BufferedImage)
	 */
	@Override
	public void transform(BufferedImage imageToChange) {
		int height = imageToChange.getHeight();
		int width = imageToChange.getWidth();
		for(int x = 0; x < width; x += 2) {
			for(int y=0; y < height/2; y++) {
				int oppositeY = height - 1 - y;
				int rgbTop = imageToChange.getRGB(x, oppositeY);
				int rgbBottom = imageToChange.getRGB(x, y);
				imageToChange.setRGB(x, oppositeY, rgbBottom);
				imageToChange.setRGB(x, y, rgbTop);

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
