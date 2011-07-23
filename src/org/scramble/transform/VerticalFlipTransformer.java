/**
 * 
 */
package org.scramble.transform;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Image transformer - vertical flip
 * @author alan
 *
 */
public class VerticalFlipTransformer implements ImageTransformer {

	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#transform(java.awt.image.BufferedImage)
	 */
	@Override
	public void transform(BufferedImage imageToChange) {
		int height = imageToChange.getHeight();
		int width = imageToChange.getWidth();
		for(int x = 0; x < width; x++) {
			for(int y=0; y < height/2; y++) {
				int oppositeY = height - 1 - y;
				int rgbTop = imageToChange.getRGB(x, oppositeY);
				int rgbBottom = imageToChange.getRGB(x, y);
				imageToChange.setRGB(x, oppositeY, rgbBottom);
				imageToChange.setRGB(x, y, rgbTop);
//				if(x < 10 && y < 10) { // TODO: remove
//					/*ColorModel model = imageToChange.getColorModel();
//					int[] compBits = model.getComponentSize();
//					int[] components = new int[compBits.length];
//					components = model.getComponents(rgbBottom, null, 0);
//					System.out.println(" " + x + "," + y + " " + rgbBottom + " components: ");
//					for(int c: components) {
//						System.out.print(" " + c);
//					}*/
//					Color color = new Color(rgbBottom);
//					System.out.println(" " + x + "," + y + " " + rgbBottom + " color: " + color.getRed() + " " + color.getGreen() + " " + color.getBlue());
//				}
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
