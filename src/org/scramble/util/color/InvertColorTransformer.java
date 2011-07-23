/**
 * 
 */
package org.scramble.util.color;

import java.awt.Color;

/**
 * @author alan
 *
 */
public class InvertColorTransformer extends ColorTransformer {

	/* (non-Javadoc)
	 * @see com.aip.scramble.util.color.ColorTransformer#reverseColorTransformation(java.awt.Color)
	 */
	@Override
	protected Color reverseColorTransformation(Color rgbColor) {
		return this.transformColor(rgbColor);
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.util.color.ColorTransformer#transformColor(java.awt.Color)
	 */
	@Override
	protected Color transformColor(Color rgbColor) {
		return new Color(invert(rgbColor.getRed()), invert(rgbColor.getGreen()), invert(rgbColor.getBlue()));
	}
	
	private int invert(int val) {
		return 255 - val;
	}
	
}
