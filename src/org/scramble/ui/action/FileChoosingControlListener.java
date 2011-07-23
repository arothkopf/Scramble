/**
 * 
 */
package org.scramble.ui.action;

import java.awt.Component;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;

import org.scramble.ApplicationState;
import org.scramble.ScrambleController;
import org.scramble.ui.SwingApp;


/**
 * Action Listener which depends on a file chooser
 * @author alan
 *
 */
public abstract class FileChoosingControlListener extends
		AbstractScrambleControlListener {
	protected final JFileChooser chooserDefault;	
	protected final JFileChooser chooserWhenScrambled;	

	/**
	 * Constructor with injection of controller and UI elements needed
	 * @param controller
	 * @param itemsToActivateOnSuccess
	 * @param itemsToActivateOnFailure
	 * @param itemsToDeactivateOnSuccess
	 * @param itemsToDeactivateOnFailure
	 * @param feedbackLabel
	 * @param chooser
	 */
	public FileChoosingControlListener(ScrambleController controller,
			List<Component> itemsToActivateOnSuccess,
			List<Component> itemsToActivateOnFailure,
			List<Component> itemsToDeactivateOnSuccess,
			List<Component> itemsToDeactivateOnFailure, SwingApp swingApp,
			JFileChooser chooser) {
		super(controller, itemsToActivateOnSuccess, itemsToActivateOnFailure,
				itemsToDeactivateOnSuccess, itemsToDeactivateOnFailure,
				swingApp);
		this.chooserDefault = chooser;
		this.chooserWhenScrambled = chooser;
	}
	
	/**
	 * Constructor with injection of controller and UI elements needed
	 * @param controller
	 * @param itemsToActivateOnSuccess
	 * @param itemsToActivateOnFailure
	 * @param itemsToDeactivateOnSuccess
	 * @param itemsToDeactivateOnFailure
	 * @param swingApp
	 * @param defaultChooser
	 * @param scrambledSaveChooser
	 */
	public FileChoosingControlListener(ScrambleController controller,
			List<Component> itemsToActivateOnSuccess,
			List<Component> itemsToActivateOnFailure,
			List<Component> itemsToDeactivateOnSuccess,
			List<Component> itemsToDeactivateOnFailure, SwingApp swingApp,
			JFileChooser defaultChooser, JFileChooser scrambledSaveChooser) {
		super(controller, itemsToActivateOnSuccess, itemsToActivateOnFailure,
				itemsToDeactivateOnSuccess, itemsToDeactivateOnFailure,
				swingApp);
		this.chooserDefault = defaultChooser;
		this.chooserWhenScrambled = scrambledSaveChooser;
	}
	
	
	/**
	 * @return File chosen by the user, or null if no file chosen
	 */
	protected File getFile() {
		JFileChooser chooser = this.chooserDefault;
		if(this.controller.getCurrentState() == ApplicationState.SCRAMBLED) {
			chooser = this.chooserWhenScrambled; // when saving a scrambled file, the file format must be lossless
		}
        int returnVal = chooser.showOpenDialog(this.swingApp.getFrame());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
	}

	
	




	
	

}
