/**
 * 
 */
package org.scramble.ui.action;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;

import org.scramble.ScrambleController;
import org.scramble.ui.SwingApp;
import org.scramble.util.log.Logger;


/**
 * @author alan
 *
 */
public abstract class AbstractScrambleControlListener implements ActionListener {
	protected final ScrambleController controller;
	protected final List<Component> itemsToActivateOnSuccess;
	protected final List<Component> itemsToActivateOnFailure;
	protected final List<Component> itemsToDeactivateOnSuccess;
	protected final List<Component> itemsToDeactivateOnFailure;
	protected final SwingApp swingApp;
	protected Logger log;

	
	
	/**
	 * Basic constructor to inject controller
	 * @param controller
	 * @param itemsToActivateOnSuccess
	 * @param itemsToActivateOnFailure
	 * @param itemsToDeactivateOnSuccess
	 * @param itemsToDeactivateOnFailure
	 * @param feedbackLabel
	 */
	public AbstractScrambleControlListener(ScrambleController controller,
			List<Component> itemsToActivateOnSuccess,
			List<Component> itemsToActivateOnFailure,
			List<Component> itemsToDeactivateOnSuccess,
			List<Component> itemsToDeactivateOnFailure, SwingApp swingApp) {
		super();
		this.controller = controller;
		this.itemsToActivateOnSuccess = itemsToActivateOnSuccess;
		this.itemsToActivateOnFailure = itemsToActivateOnFailure;
		this.itemsToDeactivateOnSuccess = itemsToDeactivateOnSuccess;
		this.itemsToDeactivateOnFailure = itemsToDeactivateOnFailure;
		this.swingApp = swingApp;
	}

	/**
	 * Resize a frame
	 * @param idealWidth
	 * @param idealHeight
	 */
	protected void resizeFrame(int idealWidth, int idealHeight) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int targetWidth = Double.valueOf(screenSize.getWidth()).intValue() - 10;
		int targetHeight = Double.valueOf(screenSize.getHeight()).intValue() - 10;
		if(idealWidth < targetWidth) {
			targetWidth = idealWidth;
		}
		if(idealHeight < targetHeight) {
			targetHeight = idealHeight;
		}
		if(null != this.swingApp) {
			this.swingApp.resizeFrame(targetWidth, targetHeight);
		}
	}
	
	
	/**
	 * Handle the event
	 * @param e
	 * @return true if successful, false if failed
	 */
	protected abstract boolean performActionSucceeded(ActionEvent e);

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.performActionSucceeded(e)) {
			this.setComponentsEnabled(true, this.itemsToActivateOnSuccess);
			this.setComponentsEnabled(false, this.itemsToDeactivateOnSuccess);
			if(null != this.swingApp) {
				this.swingApp.forceRedraw();
			}
		} else {
			this.setComponentsEnabled(true, this.itemsToActivateOnFailure);
			this.setComponentsEnabled(false, this.itemsToDeactivateOnFailure);
		}
	}

	/**
	 * Set a list of components to enabled or disabled
	 * @param enabled
	 * @param components
	 */
	protected void setComponentsEnabled(boolean enabled, List<Component> components) {
		if(null != components) {
			for(Component c: components) {
				c.setEnabled(enabled);
			}
		}
	}
}
