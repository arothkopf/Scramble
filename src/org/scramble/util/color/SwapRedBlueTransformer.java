/**
 * 
 */
package org.scramble.util.color;

import java.awt.Color;

/**
 * Transformer which swaps red and green
 * @author alan
 *
 */
public class SwapRedBlueTransformer extends ColorTransformer {

	/* (non-Javadoc)
	 * @see com.aip.scramble.util.color.ColorTransformer#reverseColorTransformation(java.awt.Color)
	 */
	@Override
	protected Color reverseColorTransformation(Color rgbColor) {		
		return new Color(rgbColor.getBlue(), rgbColor.getGreen(), rgbColor.getRed());
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.util.color.ColorTransformer#transformColor(java.awt.Color)
	 */
	@Override
	protected Color transformColor(Color rgbColor) {
		return new Color(rgbColor.getBlue(), rgbColor.getGreen(), rgbColor.getRed());
	}

}
