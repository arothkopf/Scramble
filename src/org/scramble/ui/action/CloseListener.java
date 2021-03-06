/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * @author alan
 *
 */
public class CloseListener implements ActionListener {
	private final JFrame frame;
	
	
	
	/**
	 * @param frame
	 */
	public CloseListener(JFrame frame) {
		super();
		this.frame = frame;
	}



	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.frame.dispose();
	}
	

}
