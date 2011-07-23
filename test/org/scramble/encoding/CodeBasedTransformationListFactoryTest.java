/**
 * 
 */
package org.scramble.encoding;

import java.util.Map;
import java.util.TreeMap;

import junit.framework.Assert;

import org.junit.Test;
import org.scramble.encoding.CodeBasedTransformationListFactory;
import org.scramble.transform.ImageTransformer;


/**
 * Unit test for CodeBasedTransformationListFactory
 * @author alan
 *
 */
public class CodeBasedTransformationListFactoryTest {

	/**
	 * Test method for {@link org.scramble.encoding.CodeBasedTransformationListFactory#codeToBytes(java.lang.String)}.
	 */
	@Test
	public void testCodeToBytes() {
		CodeBasedTransformationListFactory factory = new CodeBasedTransformationListFactory();
		
		int[] results1 = factory.bytesToInts0to255(factory.codeToBytes("lion tamer long long string 111111111233278945743834289879487430894387903498738793448797340"));
		int[] results2 = factory.bytesToInts0to255(factory.codeToBytes("a"));
		StringBuilder b1 = new StringBuilder();
		StringBuilder b2 = new StringBuilder();
		for(int b: results1) {
			b1.append(b);
			b1.append(' ');
		}
		for(int b: results2) {
			b2.append(b);
			b2.append(' ');
		}
		System.out.println("R1: " + b1.toString());
		System.out.println("R2: " + b2.toString());

		Assert.assertFalse(b1.toString().equals(b2.toString()));
	}
	
	private static final String[] EXPECTED_CLASSES = {
		"org.scramble.transform.HorizontalFlipTransformer",
		"org.scramble.transform.EvenColumnFlipTransformer",
		"org.scramble.transform.ColorSequenceBasedTransformer",
		"org.scramble.transform.VerticalFlipTransformer",
		"org.scramble.transform.BlockRotationTransformer",
		"org.scramble.transform.EvenLineFlipTransformer",
		"org.scramble.transform.BlockAddingTransformer",
		"org.scramble.transform.YBasedWithSequenceTransformer"
	};

	/**
	 * Test method for {@link org.scramble.encoding.CodeBasedTransformationListFactory#codeToTransformerList(byte[])}.
	 */
	@Test
	public void testCodeToTransformerList() {
		CodeBasedTransformationListFactory factory = new CodeBasedTransformationListFactory();
		Map<String, Integer> classes = new TreeMap<String, Integer>();
		byte b = -128;
		do {
			//System.out.println(b);
			checkForBytes(factory, b, classes);			
			b++;
		}while(b > -128);
		for(String c: classes.keySet()) {
			System.out.println(c);
			System.out.println(classes.get(c));
		}
		Assert.assertEquals(Integer.valueOf(32), classes.get("org.scramble.transform.BlockAddingTransformer"));
		Assert.assertEquals(Integer.valueOf(32), classes.get("org.scramble.transform.BlockRotationTransformer"));
		Assert.assertEquals(Integer.valueOf(64), classes.get("org.scramble.transform.ColorSequenceBasedTransformer"));
		Assert.assertEquals(Integer.valueOf(8), classes.get("org.scramble.transform.EvenColumnFlipTransformer"));
		Assert.assertEquals(Integer.valueOf(8), classes.get("org.scramble.transform.EvenLineFlipTransformer"));
		Assert.assertEquals(Integer.valueOf(8), classes.get("org.scramble.transform.HorizontalFlipTransformer"));
		Assert.assertEquals(Integer.valueOf(8), classes.get("org.scramble.transform.VerticalFlipTransformer"));
		Assert.assertEquals(Integer.valueOf(96), classes.get("org.scramble.transform.YBasedWithSequenceTransformer"));
	}

	/**
	 * @param factory
	 */
	private void checkForBytes(CodeBasedTransformationListFactory factory, byte lastByte, Map<String,Integer> foundClasses) {
		byte[] codeBytes = {0,0,0,0,lastByte};
		ImageTransformer[] tlist = factory.codeToTransformerList(codeBytes);
		
		Assert.assertTrue(tlist.length > 1);
		Assert.assertEquals("org.scramble.transform.ColorOnlyTransformer", tlist[0].getClass().getCanonicalName());
		
		String cname = tlist[1].getClass().getCanonicalName();
		Integer ival = foundClasses.get(cname);
		int count = 1;
		if(null != ival) {
			count = ival.intValue() + 1;
		}
		foundClasses.put(cname, Integer.valueOf(count));
	}

}
