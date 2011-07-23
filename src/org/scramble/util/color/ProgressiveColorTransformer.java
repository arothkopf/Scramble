/**
 * 
 */
package org.scramble.util.color;

import java.awt.Color;

/**
 * Cumulative transformation of color
 * @author alan
 *
 */
public class ProgressiveColorTransformer extends ColorTransformer {
	private int magnitudeRed = 1;
	private int stepRed = 1;
	private int magnitudeGreen = 1;
	private int stepGreen = 1;
	private int magnitudeBlue = 1;
	private int stepBlue = 1;
	
	/* (non-Javadoc)
	 * @see com.aip.scramble.util.color.ColorTransformer#reverseColorTransformation(java.awt.Color)
	 */
	@Override
	protected Color reverseColorTransformation(Color rgbColor) {
		incrementRed();
		incrementGreen();
		incrementBlue();
		int newRed = this.decrease(rgbColor.getRed(), this.magnitudeRed);
		int newGreen = this.decrease(rgbColor.getGreen(), this.magnitudeGreen);
		int newBlue = this.decrease(rgbColor.getBlue(), this.magnitudeBlue);
		return new Color(newRed, newGreen, newBlue, rgbColor.getAlpha());
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.util.color.ColorTransformer#transformColor(java.awt.Color)
	 */
	@Override
	protected Color transformColor(Color rgbColor) {
		incrementRed();
		incrementGreen();
		incrementBlue();
		int newRed = this.increase(rgbColor.getRed(), this.magnitudeRed);
		int newGreen = this.increase(rgbColor.getGreen(), this.magnitudeGreen);
		int newBlue = this.increase(rgbColor.getBlue(), this.magnitudeBlue);
		return new Color(newRed, newGreen, newBlue, rgbColor.getAlpha());
	}

	private void incrementRed() {
		this.magnitudeRed += this.stepRed;
		this.magnitudeRed %= 256;
	}

	private void incrementGreen() {
		this.magnitudeGreen += this.stepGreen;
		this.magnitudeGreen %= 256;
	}

	private void incrementBlue() {
		this.magnitudeBlue += this.stepBlue;
		this.magnitudeBlue %= 256;
	}
	
	private int increase(int value, int magnitude) {
		int newval = value + magnitude;
		return newval % 256;
	}

	private int decrease(int value, int magnitude) {
		int newval = value - magnitude;
		if(newval < 0) {
			newval = 256 + newval;
		}
		return newval;
	}


	/**
	 * @param magnitude the magnitude to set
	 */
	public void setMagnitudes(int magnitude) {
		this.magnitudeRed = magnitude % 255;
		this.magnitudeGreen = magnitude % 255;
		this.magnitudeBlue = magnitude % 255;
	}

	/**
	 * @return the magnitudeRed
	 */
	public int getMagnitudeRed() {
		return magnitudeRed;
	}

	/**
	 * @param magnitudeRed the magnitudeRed to set
	 */
	public void setMagnitudeRed(int magnitudeRed) {
		this.magnitudeRed = magnitudeRed;
	}

	/**
	 * @return the stepRed
	 */
	public int getStepRed() {
		return stepRed;
	}

	/**
	 * @param stepRed the stepRed to set
	 */
	public void setStepRed(int stepRed) {
		this.stepRed = stepRed;
	}

	/**
	 * @return the magnitudeGreen
	 */
	public int getMagnitudeGreen() {
		return magnitudeGreen;
	}

	/**
	 * @param magnitudeGreen the magnitudeGreen to set
	 */
	public void setMagnitudeGreen(int magnitudeGreen) {
		this.magnitudeGreen = magnitudeGreen;
	}

	/**
	 * @return the stepGreen
	 */
	public int getStepGreen() {
		return stepGreen;
	}

	/**
	 * @param stepGreen the stepGreen to set
	 */
	public void setStepGreen(int stepGreen) {
		this.stepGreen = stepGreen;
	}

	/**
	 * @return the magnitudeBlue
	 */
	public int getMagnitudeBlue() {
		return magnitudeBlue;
	}

	/**
	 * @param magnitudeBlue the magnitudeBlue to set
	 */
	public void setMagnitudeBlue(int magnitudeBlue) {
		this.magnitudeBlue = magnitudeBlue;
	}

	/**
	 * @return the stepBlue
	 */
	public int getStepBlue() {
		return stepBlue;
	}

	/**
	 * @param stepBlue the stepBlue to set
	 */
	public void setStepBlue(int stepBlue) {
		this.stepBlue = stepBlue;
	}

	/**
	 * Set the incrementation values for each color
	 * @param stepRed
	 * @param stepGreen
	 * @param stepBlue
	 */
	public void setSteps(final int stepRed, final int stepGreen, final int stepBlue) {
		this.stepRed = stepRed;
		this.stepGreen = stepGreen;
		this.stepBlue = stepBlue;
	}
	
/*	
	public static void main(String[] args) {
		Color c1 = new Color((255 + 60) % 256);
		
	}*/
	
}
