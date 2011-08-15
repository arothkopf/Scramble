/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.transform;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Transformer which combines colors from a block (effectively another image
 * @author alan
 *
 */
public class BlockAddingTransformer implements ImageTransformer {
	private final int[][] redBlock;
	private final int[][] greenBlock;
	private final int[][] blueBlock;
	
	
	/**
	 * CTOR
	 * @param redBlock
	 * @param greenBlock
	 * @param blueBlock
	 */
	public BlockAddingTransformer(int[][] redBlock, int[][] greenBlock,
			int[][] blueBlock) {
		super();
		this.redBlock = redBlock;
		this.greenBlock = greenBlock;
		this.blueBlock = blueBlock;
	}
	

	

	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#transform(java.awt.image.BufferedImage)
	 */
	@Override
	public void transform(BufferedImage imageToChange) {
		int width = imageToChange.getWidth();
		int height = imageToChange.getHeight();
		
		for(int x = 0; x < width; x++) {
			for(int y=0; y < height; y++) {
				int blockValRed = getAddValueForBlock(x, y, this.redBlock);
				int blockValGreen = getAddValueForBlock(x, y, this.greenBlock);
				int blockValBlue = getAddValueForBlock(x, y, this.blueBlock);
				setNewColorForTransform(imageToChange, x, y, blockValRed,
						blockValGreen, blockValBlue);
			}				
		}
	}



	// Set the color of the given pixel at x,y for the forward transformation
	private void setNewColorForTransform(BufferedImage imageToChange, int x,
			int y, int blockValRed, int blockValGreen, int blockValBlue) {
		Color currentColor = new Color(imageToChange.getRGB(x, y));
		int redVal = (currentColor.getRed() + blockValRed)%256;
		int greenVal = (currentColor.getGreen() + blockValGreen)%256;
		int blueVal = (currentColor.getBlue() + blockValBlue)%256;
		imageToChange.setRGB(x, y, new Color(redVal, greenVal, blueVal).getRGB());
	}



	// get the value to add for the block
	private int getAddValueForBlock(int x, int y, int[][] block) {
		int[] blockRow = block[y%block.length];
		return blockRow[x%blockRow.length]%256;
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.transform.ImageTransformer#untransform(java.awt.image.BufferedImage)
	 */
	@Override
	public void untransform(BufferedImage imageToChange) {
		int width = imageToChange.getWidth();
		int height = imageToChange.getHeight();
		
		for(int x = 0; x < width; x++) {
			for(int y=0; y < height; y++) {
				int blockValRed = getAddValueForBlock(x, y, this.redBlock);
				int blockValGreen = getAddValueForBlock(x, y, this.greenBlock);
				int blockValBlue = getAddValueForBlock(x, y, this.blueBlock);
				setNewColorForUntransform(imageToChange, x, y, blockValRed,
						blockValGreen, blockValBlue);
			}				
		}
	}



	// Set the color of the given pixel at x,y for undoing the transformation
	private void setNewColorForUntransform(BufferedImage imageToChange, int x,
			int y, int blockValRed, int blockValGreen, int blockValBlue) {
		Color currentColor = new Color(imageToChange.getRGB(x, y));
		int redVal = subtractColorValue(currentColor.getRed(), blockValRed);
		int greenVal = subtractColorValue(currentColor.getGreen(), blockValGreen);
		int blueVal = subtractColorValue(currentColor.getBlue(), blockValBlue);
		try {	
			imageToChange.setRGB(x, y, new Color(redVal, greenVal, blueVal).getRGB());
		}catch(RuntimeException e) {
			System.out.println("x=" + x + " y=" + y + " r=" + redVal + " g=" + greenVal + " b=" + blueVal);
			throw e;
		}
	}
	
	// val1 - val2
	private int subtractColorValue(int val1, int val2) {
		int val = (val1 - val2)% 256;
		if(val < 0) {
			val = 256 + val;
		}
		return val;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random r = new Random();
		System.out.println("	public static final int[][] EVEN_ODD_BLOCK_16 = {");
		for(int y=0; y <32; y++) {
			StringBuilder b = new StringBuilder();
			b.append("   {");
			for(int x=0; x < 16; x++) {
				if(x > 0) {
//					if(x%16 == 0) {
//						b.append("\n");						
//					}
					b.append(", ");
				}
				int val = x + y;
				if(x%2 == 0) {
					val += 100;
				}
				b.append(val);
//				if(x + y > 20) {
//					b.append(r.nextInt(255));
//				} else {
//					b.append(x*y%256);
//				}
			}
			b.append("}, ");
			System.out.println(b.toString());
		}
		
		System.out.println("};");
	}

	
	
}
