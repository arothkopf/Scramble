/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.util.color;

import java.awt.Color;

/**
 * Transformer which swaps green and blue
 * @author alan
 *
 */
public class SwapGreenBlueTransformer extends ColorTransformer {

	/* (non-Javadoc)
	 * @see com.aip.scramble.util.color.ColorTransformer#reverseColorTransformation(java.awt.Color)
	 */
	@Override
	protected Color reverseColorTransformation(Color rgbColor) {		
		return new Color(rgbColor.getRed(), rgbColor.getBlue(), rgbColor.getGreen());
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.util.color.ColorTransformer#transformColor(java.awt.Color)
	 */
	@Override
	protected Color transformColor(Color rgbColor) {
		return new Color(rgbColor.getRed(), rgbColor.getBlue(), rgbColor.getGreen());
	}

}
