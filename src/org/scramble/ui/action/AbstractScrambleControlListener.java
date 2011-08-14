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
	
	private static final int MIN_WIDTH = 400;
	private static final int MIN_HEIGHT = 240;

	
	
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
		int targetWidth = calculateTargetWidth(idealWidth, screenSize);
		int targetHeight = calculateTargetHeight(idealHeight, screenSize);
		if(null != this.swingApp) {
			this.swingApp.resizeFrame(targetWidth, targetHeight);
		}
	}

	/**
	 * @param idealHeight
	 * @param screenSize
	 * @return
	 */
	private int calculateTargetHeight(int idealHeight, Dimension screenSize) {
		return dimensionWithinBounds(idealHeight,
				Double.valueOf(screenSize.getHeight()).intValue() - 10,
				MIN_HEIGHT);
	}

	/**
	 * @param idealWidth
	 * @param screenSize
	 * @return the actual new width
	 */
	private int calculateTargetWidth(int idealWidth, Dimension screenSize) {
		return dimensionWithinBounds(idealWidth,
				Double.valueOf(screenSize.getWidth()).intValue() - 10,
				MIN_WIDTH);
	}

	/**
	 * @param idealValue
	 * @param maxDimension
	 * @param minDimension
	 * @return The value for the dimension (height or width), either idealValue or the upper or lower bound
	 */
	private int dimensionWithinBounds(int idealValue, int maxDimension,
			int minDimension) {
		if(idealValue < maxDimension) {
			if(idealValue > minDimension) {
				return idealValue;				
			} else {
				return minDimension;
			}
		}
		return maxDimension;
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
