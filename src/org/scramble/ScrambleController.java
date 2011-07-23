/**
 * 
 */
package org.scramble;

import java.awt.image.BufferedImage;

import org.scramble.encoding.CodeBasedTransformationListFactory;
import org.scramble.exception.FileExistsException;
import org.scramble.transform.ImageTransformer;
import org.scramble.util.file.FileSaveResult;
import org.scramble.util.file.ImageFileHelper;
import org.scramble.util.log.Logger;


/**
 * Application-level control (user-initiated operations) for image scrambling and unscrambling
 * @author alan
 *
 */
public class ScrambleController {
	private String currentFileName = null;
	private BufferedImage currentImage = null;
	private ImageFileHelper fileHelper = new ImageFileHelper();
	private CodeBasedTransformationListFactory factory = new CodeBasedTransformationListFactory();
	private ApplicationState currentState = ApplicationState.EMPTY;
	private Logger log = new Logger();
	
	/**
	 * Load an image for scrambling or unscrambling
	 * @param filePath
	 * @return success
	 */
	public boolean loadSourceImage(String filePath) {
		this.currentImage = this.fileHelper.loadImage(filePath); 
		if(null != this.currentImage) {
			this.currentState = ApplicationState.IMAGE_LOADED;
			this.currentFileName = filePath;
			this.setImageTo24Bits();
			return true;
		}
		return false;
	}
	
	protected void setImageTo24Bits() {
		if(!this.fileHelper.is24Bits(this.currentImage)) {
			this.currentImage = this.fileHelper.convertTo24BitColorCopy(this.currentImage);
		}
	}
	
	
	/**
	 * Save the transformed image, even if a file with the same name exists
	 * @param filePath
	 * @return success
	 */
	public boolean saveDestImageWithOverwrite(String filePath) {
		if(this.fileHelper.saveImage(this.currentImage, filePath)) {
			this.currentState = ApplicationState.SAVED;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Save the transformed image, even if a file with the same name exists
	 * @param filePath
	 * @return success
	 */
	public FileSaveResult saveDestImageNoOverwrite(String filePath) throws FileExistsException {
		if(this.fileHelper.fileExists(filePath)) {
			return FileSaveResult.BLOCKED_FILE_EXISTS;
		}
		if(this.fileHelper.saveImage(this.currentImage, filePath)) {
			this.currentState = ApplicationState.SAVED;
			return FileSaveResult.SUCCESS;
		} else {
			return FileSaveResult.FAILURE;			
		}
	}

	/**
	 * Scramble
	 * @param password Scrambling key
	 * @return success
	 */
	public boolean scrambleSourceImageToDest(final String password) {
		try {
			ImageTransformer[] transformers = this.factory.getTransformersFromPassword(password);
			for(int i=0; i < transformers.length; i++) {
				transformers[i].transform(this.currentImage);
			}
			this.currentState = ApplicationState.SCRAMBLED;
			return true;
		}catch(Exception e) {
			this.log.logError("ScrambleController.scrambleSourceImageToDest: " + e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * Unscramble
	 * @param password Scrambling key 
	 * @return success
	 */
	public boolean unscrambleSourceImageToDest(final String password) {
		try {
			ImageTransformer[] transformers = this.factory.getTransformersFromPassword(password);
			for(int i=transformers.length - 1; i >= 0; i--) {
				transformers[i].untransform(this.currentImage);
			}
			this.currentState = ApplicationState.UNSCRAMBLED;
			return true;
		}catch(Exception e) {
			this.log.logError("ScrambleController.scrambleSourceImageToDest: " + e.getMessage(), e);
			return false;
		}
	}

	/**
	 * @return the currentFileName
	 */
	public String getCurrentFileName() {
		return currentFileName;
	}
	
	
	
	/**
	 * @return the currentState
	 */
	public ApplicationState getCurrentState() {
		return currentState;
	}

	/**
	 * @param currentState the currentState to set
	 */
	public void setCurrentState(ApplicationState currentState) {
		this.currentState = currentState;
	}

	/**
	 * @return the currentImage
	 */
	public BufferedImage getCurrentImage() {
		return currentImage;
	}

	/**
	 * @return Image width
	 */
	public int getCurrentImageWidth() {
		int width = 0;
		if(null != this.currentImage) {
			width = this.currentImage.getWidth();
		}
		return width;
	}
	
	/**
	 * @return Image height
	 */
	public int getCurrentImageHeight() {
		int height = 0;
		if(null != this.currentImage) {
			height = this.currentImage.getHeight();
		}
		return height;
	}

	/**
	 * Set the helper (for unit tests)
	 * @param helper
	 */
	protected void setFileHelper(ImageFileHelper helper) {
		this.fileHelper = helper;
	}
}
