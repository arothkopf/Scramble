/**
 * 
 */
package org.scramble;

import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.scramble.exception.FileExistsException;
import org.scramble.util.file.FileSaveResult;
import org.scramble.util.file.ImageFileHelper;

/**
 * Unit tests for ScrambleController
 * @author alan
 *
 */
public class ScrambleControllerTest {
	ScrambleController controller = new ScrambleController();
	
	private static class TestFileHelper extends ImageFileHelper {
		private BufferedImage savedImage;
		private String savedImagePath;
		private boolean fileExistsIntern = false;
		
		private BufferedImage testedImage24 = null;

		/* (non-Javadoc)
		 * @see org.scramble.util.file.ImageFileHelper#saveImage(java.awt.image.BufferedImage, java.lang.String)
		 */
		@Override
		public boolean saveImage(BufferedImage bi, String filePath) {
			this.savedImagePath = filePath;
			this.savedImage = bi;
			return true;
		}
		
		

/*		 (non-Javadoc)
		 * @see org.scramble.util.file.ImageFileHelper#convertTo24BitColorCopy(java.awt.image.BufferedImage)
		 
		@Override
		public BufferedImage convertTo24BitColorCopy(BufferedImage imageIn) {
			this.convertImage = imageIn;
			return imageIn;
		}*/
		

		/* (non-Javadoc)
		 * @see org.scramble.util.file.ImageFileHelper#fileExists(java.lang.String)
		 */
		@Override
		public boolean fileExists(String imagePath) {
			return this.fileExistsIntern;
		}



		/* (non-Javadoc)
		 * @see org.scramble.util.file.ImageFileHelper#is24Bits(java.awt.image.BufferedImage)
		 */
		@Override
		public boolean is24Bits(BufferedImage img) {
			this.testedImage24 = img;
			return super.is24Bits(img);
		}
		
	}

	/**
	 * Test method for {@link org.scramble.ScrambleController#loadSourceImage(java.lang.String)}.
	 */
	@Test
	public void testLoadSourceImage() {
		this.controller.loadSourceImage(ClassLoader.getSystemResource("scramble1.gif").getFile());
		BufferedImage image = this.controller.getCurrentImage();
		Assert.assertNotNull("Image not loaded", image);
		String fname = this.controller.getCurrentFileName();
		Assert.assertTrue(fname.endsWith("/scramble1.gif"));
	}

	
	
	
	/**
	 * Test method for {@link org.scramble.ScrambleController#setImageTo24Bits()}.
	 */
	@Test
	public void testSetImageTo24Bits() {
		this.controller.loadSourceImage(ClassLoader.getSystemResource("scramble1.gif").getFile());
		TestFileHelper helper = new TestFileHelper();
		this.controller.setFileHelper(helper);
		BufferedImage image1 = this.controller.getCurrentImage();
		this.controller.setImageTo24Bits();
		Assert.assertSame(image1, helper.testedImage24);
	}

	/**
	 * Test method for {@link org.scramble.ScrambleController#saveDestImageWithOverwrite(java.lang.String)}.
	 */
	@Test
	public void testSaveDestImageWithOverwrite() {
		try {
			this.controller.loadSourceImage(ClassLoader.getSystemResource("scramble1.gif").getFile());
			TestFileHelper helper = new TestFileHelper();
			this.controller.setFileHelper(helper);

			String filePath = "whatever.gif";
			FileSaveResult result = this.controller.saveDestImageNoOverwrite(filePath);
			
			Assert.assertEquals(FileSaveResult.SUCCESS, result);
			Assert.assertNotNull(helper.savedImage);
			Assert.assertEquals(filePath, helper.savedImagePath);
		} catch (FileExistsException e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}

	/**
	 * Test method for {@link org.scramble.ScrambleController#saveDestImageNoOverwrite(java.lang.String)}.
	 */
	@Test
	public void testSaveDestImageNoOverwrite() {
		try {
			this.controller.loadSourceImage(ClassLoader.getSystemResource("scramble1.gif").getFile());
			TestFileHelper helper = new TestFileHelper();
			helper.fileExistsIntern = true;
			this.controller.setFileHelper(helper);

			String filePath = "whatever.gif";
			FileSaveResult result = this.controller.saveDestImageNoOverwrite(filePath);
			
			Assert.assertEquals(FileSaveResult.BLOCKED_FILE_EXISTS, result);
		} catch (FileExistsException e) {
			e.printStackTrace();
			Assert.fail(e.toString());
		}
	}

	/**
	 * Test method for {@link org.scramble.ScrambleController#scrambleSourceImageToDest(java.lang.String)}.
	 */
	@Test
	public void testScrambleSourceImageToDest() {
		this.controller.loadSourceImage(ClassLoader.getSystemResource("scramble1.gif").getFile());
		this.controller.setImageTo24Bits();
		int rgb1 = this.controller.getCurrentImage().getRGB(1, 1);
		
		this.controller.scrambleSourceImageToDest("password");
		
		int rgb2 = this.controller.getCurrentImage().getRGB(1, 1);
		Assert.assertFalse("Unchanged rgb value", rgb1 == rgb2);
		
	}

	Color[] getColorArray(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		Color[] colors = new Color[width * height];
		int count = 0;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				colors[count++] = new Color(image.getRGB(x, y));
			}	
		}
		
		return colors;
	}
	
	/**
	 * Test method for {@link org.scramble.ScrambleController#unscrambleSourceImageToDest(java.lang.String)}.
	 */
	@Test
	public void testUnscrambleSourceImageToDest() {
		this.controller.loadSourceImage(ClassLoader.getSystemResource("scramble1.gif").getFile());
		
		Color[] rgb1 = this.getColorArray(this.controller.getCurrentImage());

		this.controller.scrambleSourceImageToDest("password");
		
		this.controller.unscrambleSourceImageToDest("password");
		
		Color[] rgb2 = this.getColorArray(this.controller.getCurrentImage());
		
		new ImageFileHelper().saveImage(this.controller.getCurrentImage(), "C:/test/test.png");
		for(int i=0; i < rgb1.length; i++) {
			compareColors(rgb1[i], rgb2[i]);			
		}
	}


	/*
	 * @param rgb1
	 * @param rgb2
	 */
	private void compareColors(Color rgb1, Color rgb2) {
		Assert.assertTrue("Changed red", rgb1.getRed() == rgb2.getRed());
		Assert.assertTrue("Changed green", rgb1.getGreen() == rgb2.getGreen());
		Assert.assertTrue("Changed blue", rgb1.getBlue() == rgb2.getBlue());
	}

	/**
	 * Test method for {@link org.scramble.ScrambleController#getCurrentFileName()}.
	 */
	@Test
	public void testGetCurrentFileName() {
		this.controller = new ScrambleController();
		Assert.assertNull(this.controller.getCurrentFileName());
		String fname = "scramble1.gif";
		this.controller.loadSourceImage(ClassLoader.getSystemResource(fname).getFile());
		Assert.assertTrue(this.controller.getCurrentFileName().endsWith(fname));
	}

	/**
	 * Test method for {@link org.scramble.ScrambleController#getCurrentState()}.
	 */
	@Test
	public void testGetCurrentState() {
		this.controller = new ScrambleController();
		Assert.assertSame(ApplicationState.EMPTY, this.controller.getCurrentState());
		String fname = "scramble1.gif";
		this.controller.loadSourceImage(ClassLoader.getSystemResource(fname).getFile());
		Assert.assertSame(ApplicationState.IMAGE_LOADED, this.controller.getCurrentState());
		
		this.controller.scrambleSourceImageToDest("password");
		Assert.assertSame(ApplicationState.SCRAMBLED, this.controller.getCurrentState());
				
		this.controller.unscrambleSourceImageToDest("password");
		Assert.assertSame(ApplicationState.UNSCRAMBLED, this.controller.getCurrentState());

		this.controller.setFileHelper(new TestFileHelper());
		this.controller.saveDestImageWithOverwrite(fname);
		Assert.assertSame(ApplicationState.SAVED, this.controller.getCurrentState());
		
	}

	/**
	 * Test method for {@link org.scramble.ScrambleController#setCurrentState(org.scramble.ApplicationState)}.
	 */
	@Test
	public void testSetCurrentState() {
		this.controller = new ScrambleController();
		Assert.assertSame(ApplicationState.EMPTY, this.controller.getCurrentState());
		this.controller.setCurrentState(ApplicationState.SAVED);
		Assert.assertSame(ApplicationState.SAVED, this.controller.getCurrentState());
	}


	/**
	 * Test method for {@link org.scramble.ScrambleController#getCurrentImageWidth()}.
	 */
	@Test
	public void testGetCurrentImageWidthAndHeight() {
		String fname = "scramble1.gif";
		this.controller.loadSourceImage(ClassLoader.getSystemResource(fname).getFile());
		
		Assert.assertEquals(16, this.controller.getCurrentImageHeight());
		Assert.assertEquals(16, this.controller.getCurrentImageWidth());

	}

}
