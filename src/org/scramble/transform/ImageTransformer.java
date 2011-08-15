/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.transform;

import java.awt.image.BufferedImage;

/**
 * Interface for a reversible image transformation
 * @author alan
 *
 */
public interface ImageTransformer {
	/**
	 * Transform the image's data
	 * @param imageToChange
	 */
	public void transform(BufferedImage imageToChange);

	/**
	 * Reverse the transformation of the image's data
	 * @param imageToChange
	 */
	public void untransform(BufferedImage imageToChange);
}
