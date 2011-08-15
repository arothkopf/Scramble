/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.ui.action;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.Timer;

/**
 * Action listener for "About Scramble" menu item
 * @author alan
 *
 */
public class AboutListener implements ActionListener {
	private static final int ABOUT_TIMEOUT_MILLISECONDS = 5000;

	private Component popupOwner;
	
	private Component aboutPopupContents;
	
	
	public AboutListener(Component popupOwner, Component aboutPopupContents) {
		super();
		this.popupOwner = popupOwner;
		this.aboutPopupContents = aboutPopupContents;
	}




	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		PopupFactory popfact = PopupFactory.getSharedInstance();
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, this.aboutPopupContents.getWidth(),
				this.aboutPopupContents.getHeight());
		frame.add(this.aboutPopupContents);
		frame.setBackground(Color.WHITE);

		int xForPopup = getX();
		int yForPopup = getY();
		//System.out.println("x=" + xForPopup);
		//System.out.println("y=" + yForPopup);

		Popup tentativeAboutPopup = popfact.getPopup(
				this.aboutPopupContents, this.aboutPopupContents, xForPopup,
				yForPopup);
		final Popup currentAboutPopup;
		if(this.aboutPopupContents.getWidth() < 1) {
			// need to reposition because if the popup contents have never been shown, the width and height is unknown
			tentativeAboutPopup.show();
			tentativeAboutPopup.hide();
			currentAboutPopup = popfact.getPopup(
					this.aboutPopupContents, this.aboutPopupContents, this.getX(),
					this.getY());			
		} else {
			currentAboutPopup = tentativeAboutPopup;
		}
		currentAboutPopup.show();
		ActionListener hider = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentAboutPopup.hide();
			}
		};
		// Hide popup after a giving a few seconds to read it
		Timer timer = new Timer(ABOUT_TIMEOUT_MILLISECONDS, hider);
		timer.start();
	}



	/**
	 * @return
	 */
	protected int getX() {
		int contentsWidth = this.aboutPopupContents.getWidth();
		int ownerWidth = this.popupOwner.getWidth();
		return this.popupOwner.getX() + ownerWidth / 2 - contentsWidth / 2;
	}




	/**
	 * @return
	 */
	protected int getY() {
		return this.popupOwner.getY() + this.popupOwner.getHeight() / 2 - this.aboutPopupContents.getHeight() / 2;
	}

}
