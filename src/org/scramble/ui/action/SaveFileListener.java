/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.ui.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;

import org.scramble.ApplicationState;
import org.scramble.ScrambleController;
import org.scramble.exception.FileExistsException;
import org.scramble.ui.SwingApp;
import org.scramble.util.file.FileSaveResult;


/**
 * @author alan
 *
 */
public class SaveFileListener extends FileChoosingControlListener {

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
	public SaveFileListener(ScrambleController controller,
			List<Component> itemsToActivateOnSuccess,
			List<Component> itemsToActivateOnFailure,
			List<Component> itemsToDeactivateOnSuccess,
			List<Component> itemsToDeactivateOnFailure, SwingApp swingApp,
			JFileChooser defaultChooser, JFileChooser scrambledSaveChooser) {
		super(controller, itemsToActivateOnSuccess, itemsToActivateOnFailure,
				itemsToDeactivateOnSuccess, itemsToDeactivateOnFailure, swingApp,
				defaultChooser, scrambledSaveChooser);
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.ui.action.AbstractScrambleControlListener#performActionSucceeded(java.awt.event.ActionEvent)
	 */
	@Override
	protected boolean performActionSucceeded(ActionEvent e) {
		File fileToSave = this.getFile();
		if(null == fileToSave) {
			return false;
		}
		String pathLC = fileToSave.getName().toLowerCase();
		if(needsPNGFileExtension(pathLC)) {
			fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
		}
		
		try {
			FileSaveResult result = this.controller.saveDestImageNoOverwrite(fileToSave.getAbsolutePath());
			if(result == FileSaveResult.BLOCKED_FILE_EXISTS) { // TODO: ask user to overwrite?
				this.swingApp.setTextToDisplay("file.already.exists", fileToSave.getAbsolutePath());
				return false;				
			} else if(result == FileSaveResult.FAILURE) {
				this.swingApp.setTextToDisplay("file.save.failed", fileToSave.getAbsolutePath());
				return false;				
			}
		} catch (FileExistsException e1) {
			this.log.logError("Error reading file " + fileToSave.getAbsolutePath(), e1);
			return false;
		}
		this.swingApp.setTextToDisplay("file.saved", fileToSave.getAbsolutePath());
		return true;
	}

	/*
	 * Do we need to add the .png extension to the file path? 
	 * @param pathLC
	 * @return true if the extension is needed
	 */
	private boolean needsPNGFileExtension(String pathLC) {
		return needToForceFileType(pathLC) || fileHasNoExtension(pathLC);
	}

	/**
	 * @param pathLC
	 * @return true if the file path has no extension
	 */
	private boolean fileHasNoExtension(String pathLC) {
		return !pathLC.endsWith(".png") && !pathLC.endsWith(".gif") && !pathLC.endsWith(".jpg");
	}

	/**
	 * @param pathLC
	 * @return true if the file path does not end in .png and it's required to do so
	 */
	private boolean needToForceFileType(String pathLC) {
		return (this.controller.getCurrentState() == ApplicationState.SCRAMBLED && !pathLC.endsWith(".png"));
	}

}
