/**
 * 
 */
package org.scramble.util.color;

import static org.junit.Assert.fail;

import java.awt.Color;

import junit.framework.Assert;

import org.junit.Test;
import org.scramble.util.color.InvertColorTransformer;

/**
 * @author alan
 *
 */
public class InvertColorTransformerTest {

	/**
	 * Test method for {@link org.scramble.util.color.ColorTransformer#transform(int)}.
	 */
	@Test
	public void testTransform() {
		Color c = new Color(0,100,255);
		InvertColorTransformer trans = new InvertColorTransformer();
		
		int transformedVal = trans.transform(c.getRGB());
		Color c2 = new Color(transformedVal);
		Assert.assertEquals(255, c2.getRed());
		Assert.assertEquals(155, c2.getGreen());
		Assert.assertEquals(0, c2.getBlue());
	}

	/**
	 * Test method for {@link org.scramble.util.color.ColorTransformer#reverseTransformation(int)}.
	 */
	@Test
	public void testReverseTransformation() {
		Color c = new Color(255,155,0);
		InvertColorTransformer trans = new InvertColorTransformer();
		
		int transformedVal = trans.reverseTransformation(c.getRGB());
		Color c2 = new Color(transformedVal);
		Assert.assertEquals(0, c2.getRed());
		Assert.assertEquals(100, c2.getGreen());
		Assert.assertEquals(255, c2.getBlue());
	}

}
