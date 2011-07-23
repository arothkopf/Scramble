/**
 * 
 */
package org.scramble.util.color;

import java.awt.Color;

/**
 * Class to transform the color of a pixel
 * @author alan
 *
 */
public abstract class ColorTransformer {

	/**
	 * Transform the rgb value
	 * @param pixel
	 * @return Transformed value
	 */
	 public int transform(final int pixel) {
			Color rgbColor = new Color(pixel);
			Color newColor = this.transformColor(rgbColor);
			return newColor.getRGB();
	 }

	 /**
	  * Un-transform the rgb value	
	  * @param pixel
	  * @return
	  */
	 public int reverseTransformation(final int pixel) {
			Color rgbColor = new Color(pixel);
			return this.reverseColorTransformation(rgbColor).getRGB();
	 }

	 /**
	  * Perform the transformation
	  * @param rgbColor
	  * @return
	  */
	 protected abstract Color transformColor(Color rgbColor);
	
	 /**
	  * Perform the reverse transformation
	  * @param rgbColor
	  * @return
	  */
	 protected abstract Color reverseColorTransformation(Color rgbColor);
}
