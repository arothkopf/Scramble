/**
 * 
 */
package org.scramble.util.color;

import static org.junit.Assert.fail;

import java.awt.Color;

import junit.framework.Assert;

import org.junit.Test;
import org.scramble.transform.ColorOnlyTransformer;
import org.scramble.transform.ImageTransformer;
import org.scramble.util.color.ProgressiveColorTransformer;


/**
 * @author alan
 *
 */
public class ProgressiveColorTransformerTest {

	/**
	 * Test method for {@link org.scramble.util.color.ProgressiveColorTransformer#transformColor(java.awt.Color)}.
	 */
	@Test
	public void testTransformColor() {
		Color c = new Color(0,100,255);
		ProgressiveColorTransformer trans = new ProgressiveColorTransformer();
		
		int transformedVal = trans.transform(c.getRGB());
		Color c2 = new Color(transformedVal);
		Assert.assertEquals(2, c2.getRed());
		Assert.assertEquals(102, c2.getGreen());
		Assert.assertEquals(1, c2.getBlue());

		transformedVal = trans.transform(c.getRGB());
		c2 = new Color(transformedVal);
		Assert.assertEquals(3, c2.getRed());
		Assert.assertEquals(103, c2.getGreen());
		Assert.assertEquals(2, c2.getBlue());
		
		ProgressiveColorTransformer trans2 = this.makeProgressiveColorTransformer(29, 31, 226, 223);
		int val = trans2.transform(-1);
		Assert.assertEquals(-12845317, val);
	}
	
	private ProgressiveColorTransformer makeProgressiveColorTransformer(int magnitude, int stepRed, int stepGreen, int stepBlue) {
		ProgressiveColorTransformer trans = new ProgressiveColorTransformer();
		trans.setMagnitudes(magnitude);
		trans.setStepRed(stepRed);
		trans.setStepGreen(stepGreen);
		trans.setStepBlue(stepBlue);
		
		return trans;
	}


	/**
	 * Test method for {@link org.scramble.util.color.ProgressiveColorTransformer#reverseColorTransformation(java.awt.Color)}.
	 */
	@Test
	public void testReverseColorTransformation() {
		Color c = new Color(2,102,1);
		ProgressiveColorTransformer trans = new ProgressiveColorTransformer();
		
		int transformedVal = trans.reverseTransformation(c.getRGB());
		Color c2 = new Color(transformedVal);
		Assert.assertEquals(0, c2.getRed());
		Assert.assertEquals(100, c2.getGreen());
		Assert.assertEquals(255, c2.getBlue());
		
		transformedVal = trans.reverseTransformation(new Color(3,103,2).getRGB());
		c2 = new Color(transformedVal);
		Assert.assertEquals(0, c2.getRed());
		Assert.assertEquals(100, c2.getGreen());
		Assert.assertEquals(255, c2.getBlue());
	}

}
