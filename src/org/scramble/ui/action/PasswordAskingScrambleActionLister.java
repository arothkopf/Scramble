/**
 * 
 */
package org.scramble.ui.action;

import java.awt.Component;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.scramble.ScrambleController;
import org.scramble.ui.SwingApp;


/**
 * Action listener abstract class providing password-retrieving ability
 * @author alan
 *
 */
public abstract class PasswordAskingScrambleActionLister extends
		AbstractScrambleControlListener {
	private Component parentComponent;
	private ResourceBundle translator;
	
	
	/**
	 * CTOR
	 * @param controller
	 * @param itemsToActivateOnSuccess
	 * @param itemsToActivateOnFailure
	 * @param itemsToDeactivateOnSuccess
	 * @param itemsToDeactivateOnFailure
	 * @param swingApp
	 * @param parentComponent
	 * @param trans
	 */
	public PasswordAskingScrambleActionLister(ScrambleController controller,
			List<Component> itemsToActivateOnSuccess,
			List<Component> itemsToActivateOnFailure,
			List<Component> itemsToDeactivateOnSuccess,
			List<Component> itemsToDeactivateOnFailure, SwingApp swingApp,
			Component parentComponent, ResourceBundle trans) {
		super(controller, itemsToActivateOnSuccess, itemsToActivateOnFailure,
				itemsToDeactivateOnSuccess, itemsToDeactivateOnFailure,
				swingApp);
		this.parentComponent = parentComponent;
		this.translator = trans;
	}



	protected String getPasswordForOperation() {
		String s = (String)JOptionPane.showInputDialog(
		                    this.parentComponent,
		                    this.translator.getString("password.dialog.text") + "\n",
		                    this.translator.getString("password.dialog.title"),
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    null,
		                    "");

		return s;
	}

}
