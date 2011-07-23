/**
 * 
 */
package org.scramble.util.file;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.scramble.transform.BlockAddingTransformer;
import org.scramble.transform.ImageTransformer;
import org.scramble.transform.data.RandomBlocks;


/**
 * Class to handle image loading, scrambling, unscrambling and saving
 * @author alan
 *
 */
public class ImageFileHelper {

	/**
	 * Load an image from a file
	 * @param imagePath
	 * @return
	 */
	public BufferedImage loadImage(final String imagePath) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			this.logError("Error loading " + imagePath, e);
		}
		return img;
	}
	
	/**
	 * Does the file exist already?
	 * @param imagePath
	 * @return
	 */
	public boolean fileExists(final String imagePath) {
		return new File(imagePath).exists();
	}

	
	public boolean saveImage(final BufferedImage bi, final String filePath) {
		try {
		    File outputfile = new File(filePath);
		    ImageIO.write(bi, getExtension(filePath), outputfile);
		} catch (IOException e) {
			this.logError("Error saving " + filePath, e);
			return false;
		}
		return true;
	}
	
	String getExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		if(dotIndex < 0 || fileName.length() < dotIndex + 1) {
			return "jpg";
		}
		
		return fileName.substring(dotIndex + 1).toLowerCase();
	}
	
	/**
	 * Check whether the image accepts 24-bit color
	 * @param img
	 * @return true if it's in 24-bit color, false if less than 24 bits
	 */
	public boolean is24Bits(BufferedImage img) {
		if(null == img) {
			return true;
		}
		boolean is24bits = false;
		int rgbOrig = img.getRGB(0, 0);
		Color tmpColor = new Color(200, 201, 202);
		img.setRGB(0, 0, tmpColor.getRGB());
		if(img.getRGB(0, 0) == tmpColor.getRGB()) {
			is24bits = true;  // no loss of precision detected, so it's 24 bits
		}
		img.setRGB(0, 0, rgbOrig);
		return is24bits;
	}
	
	/**
	 * Copy the image to add color precision
	 * @param imageIn
	 * @return Image with 24-bit color
	 */
	public BufferedImage convertTo24BitColorCopy(BufferedImage imageIn) {
		int width = imageIn.getWidth();
		int height = imageIn.getHeight();

		BufferedImage imageOut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				imageOut.setRGB(x, y, imageIn.getRGB(x, y));
			}
		}
		return imageOut;
	}

	void logError(String msg, Exception e) {
		e.printStackTrace();
	}
	
	public static void main(String[] args) {
		ImageFileHelper proc = new ImageFileHelper();
		
		//ArrayList<ColorTransformer> seq = new ArrayList<ColorTransformer>();
		//seq.add(new SwapGreenBlueTransformer());
		//seq.add(new ProgressiveColorTransformer());
		
		BufferedImage bi = proc.loadImage("C:/test/card001.jpg");
		//ProgressiveColorTransformer ct = new ProgressiveColorTransformer();
		//ct.setMagnitudes(99);
		//ct.setSteps(20,30,90);
		//ImageTransformer trans = new ColorOnlyTransformer(ct);//new ColorProgressiveTransformer(seq);
		//ImageTransformer trans1 = new BlockRotationTransformer(40);//YBasedWithSequenceTransformer.WAVE_TRANSFORMER_VERTICAL;
		//ImageTransformer trans2 = new BlockRotationTransformer(120);
		//trans1.transform(bi);
		//trans2.transform(bi);
		int[][] block = RandomBlocks.RANDOM_BLOCK_3_32;
		int[][] block2 = RandomBlocks.RANDOM_BLOCK_1_64;

		ImageTransformer trans = new BlockAddingTransformer(block, 
				block, 
				block);
		
		ImageTransformer trans2 = new BlockAddingTransformer(block2, 
				block2, 
				block2);
		trans.transform(bi);
		proc.saveImage(bi, "C:/test/cardBlockAdd4.jpg");
		
		// ideas - different step for each color in prog transf
		// block add transformer
		// for each byte after the 1st 4 from the MD5, need choice of 16 possible transformers, and for each transformer 16 possible transformations	
		// So each byte adds 1 (or possibly 2, for non-configurable transformers) transformers to the list.  Normally we should end up with 20 or 30 transformers in sequence.  
		// use MD5 hash of password string to give all parameter values, including a block for block transformer
		/* example MD5 code: 
		  1 import java.security.*;
    2 import java.math.*;
    3 
    4 public class MD5 {
    5    public static void main(String args[]) throws Exception{
    6       String s="This is a test";
    7       MessageDigest m=MessageDigest.getInstance("MD5");
    8       m.update(s.getBytes(),0,s.length());
    9       System.out.println("MD5: "+new BigInteger(1,m.digest()).toString(16));
   10    }
   11 }

		 * */
		
		
//		seq = new ArrayList<ColorTransformer>();
//		seq.add(new SwapGreenBlueTransformer());
//		seq.add(new ProgressiveColorTransformer());
		//trans = new ColorProgressiveTransformer(seq);
		//trans2.untransform(bi);
		//ct.setMagnitudes(99);
		//ct.setSteps(20,30,90);
		trans.untransform(bi);
		proc.saveImage(bi, "C:/test/card003.jpg");

	}
}
