/**
 * 
 */
package org.scramble.transform;

import java.awt.image.BufferedImage;
import java.util.List;

import org.scramble.util.color.ColorTransformer;


/**
 * @author alan
 *
 */
public class ColorSequenceBasedTransformer extends ColorOnlyTransformer {
	protected final ColorTransformer[] transformers;
	protected int index = 0;
	
	/**
	 * @param pixelTransformer
	 */
	public ColorSequenceBasedTransformer(List<ColorTransformer> pixelTransformerSequence) {
		super(null);
		this.transformers = pixelTransformerSequence.toArray(new ColorTransformer[0]);
	}

	/**
	 * @param pixelTransformer
	 */
	public ColorSequenceBasedTransformer(ColorTransformer[] pixelTransformerSequence) {
		super(null);
		this.transformers = pixelTransformerSequence;
	}
	
	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ColorOnlyTransformer#modifyPixel(java.awt.image.BufferedImage, int, int, int)
	 */
	@Override
	protected void modifyPixel(BufferedImage imageToChange, int y, int x,
			int rgb) {
		this.nextTransformer();
		super.modifyPixel(imageToChange, y, x, rgb);
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ColorOnlyTransformer#unmodifyPixel(java.awt.image.BufferedImage, int, int, int)
	 */
	@Override
	protected void unmodifyPixel(BufferedImage imageToChange, int y, int x,
			int rgb) {
		this.nextTransformer();
		super.unmodifyPixel(imageToChange, y, x, rgb);
	}

	protected void nextTransformer() {
		this.pixelTransformer = this.transformers[this.index];
		this.index = (this.index + 1) % this.transformers.length;
	}
	
}
