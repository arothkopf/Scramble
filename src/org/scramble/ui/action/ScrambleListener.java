/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.ui.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ResourceBundle;

import org.scramble.ScrambleController;
import org.scramble.ui.SwingApp;


/**
 * @author alan
 *
 */
public class ScrambleListener extends PasswordAskingScrambleActionLister {

	/**
	 * CTOR
	 * @param controller
	 * @param itemsToActivateOnSuccess
	 * @param itemsToActivateOnFailure
	 * @param itemsToDeactivateOnSuccess
	 * @param itemsToDeactivateOnFailure
	 * @param swingApp
	 * @param parentComponent
	 * @param translator
	 */
	public ScrambleListener(ScrambleController controller,
			List<Component> itemsToActivateOnSuccess,
			List<Component> itemsToActivateOnFailure,
			List<Component> itemsToDeactivateOnSuccess,
			List<Component> itemsToDeactivateOnFailure, SwingApp swingApp,
			Component parentComponent, ResourceBundle translator) {
		super(controller, itemsToActivateOnSuccess, itemsToActivateOnFailure,
				itemsToDeactivateOnSuccess, itemsToDeactivateOnFailure, swingApp,
				parentComponent, translator);
	}

	/* (non-Javadoc)
	 * @see com.aip.scramble.ui.action.AbstractScrambleControlListener#performActionSucceeded(java.awt.event.ActionEvent)
	 */
	@Override
	protected boolean performActionSucceeded(ActionEvent e) {
		String password = this.getPasswordForOperation();
		
		this.controller.scrambleSourceImageToDest(password);
		this.swingApp.setTextToDisplay("image.scrambled","");

		return true;
	}

}
