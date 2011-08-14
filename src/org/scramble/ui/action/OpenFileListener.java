/**
 * 
 */
package org.scramble.ui.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import org.scramble.ScrambleController;
import org.scramble.ui.SwingApp;


/**
 * Action listener for open file command
 * @author alan
 *
 */
public class OpenFileListener extends FileChoosingControlListener {

	
	

	/**
	 * 
	 * @param controller
	 * @param itemsToActivateOnSuccess
	 * @param itemsToActivateOnFailure
	 * @param itemsToDeactivateOnSuccess
	 * @param itemsToDeactivateOnFailure
	 * @param feedbackLabel
	 * @param chooser
	 */
	public OpenFileListener(ScrambleController controller,
			List<Component> itemsToActivateOnSuccess,
			List<Component> itemsToActivateOnFailure,
			List<Component> itemsToDeactivateOnSuccess,
			List<Component> itemsToDeactivateOnFailure, SwingApp swingApp,
			JFileChooser chooser) {
		super(controller, itemsToActivateOnSuccess, itemsToActivateOnFailure,
				itemsToDeactivateOnSuccess, itemsToDeactivateOnFailure, swingApp,
				chooser);
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.ui.action.AbstractScrambleControlListener#performActionSucceeded(java.awt.event.ActionEvent)
	 */
	@Override
	protected boolean performActionSucceeded(ActionEvent e) {
		File fileToOpen = this.getFile();
		if(null == fileToOpen) {
			return false;
		}
		if(this.controller.loadSourceImage(fileToOpen.getAbsolutePath())) {
			this.swingApp.setTextToDisplay("image.loaded", fileToOpen.getAbsolutePath());
			
			this.swingApp.setImageToDisplay(this.controller.getCurrentImage());
			this.resizeFrame(calculateNewWidth(), calculateNewHeight());
			return true;			
		}
		this.swingApp.setTextToDisplay("image.load.failed", fileToOpen.getAbsolutePath());
		return false;			

	}

	/**
	 * @return
	 */
	private int calculateNewHeight() {
		return this.controller.getCurrentImageHeight() + 80;
	}

	/**
	 * @return
	 */
	private int calculateNewWidth() {
		return this.controller.getCurrentImageWidth()*2 + 30;
	}

}
