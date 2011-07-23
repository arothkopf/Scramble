/**
 * 
 */
package org.scramble.ui.action;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.scramble.ApplicationState;
import org.scramble.ScrambleController;
import org.scramble.ui.SwingApp;
import org.scramble.util.I18NMessageCapable;

/**
 * @author alan
 *
 */
public class HelpListener extends I18NMessageCapable implements ActionListener {
	private static final Color STATE_BASED_MESSAGE_COLOR = new Color(230, 240, 230);
	private Component popupOwner;
	private ScrambleController controller;

	

	/**
	 * @param popupOwner
	 * @param controller
	 * @param bundleName
	 */
	public HelpListener(Component popupOwner, ScrambleController controller,
			String bundleName) {
		super(bundleName);
		this.popupOwner = popupOwner;
		this.controller = controller;
	}



	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JOptionPane.showMessageDialog(this.popupOwner, makeHelpMessage(), this.getString("help.popup.title"), JOptionPane.PLAIN_MESSAGE);
	}



	/**
	 * @return The help message to display
	 */
	private Object makeHelpMessage() {
		JPanel messagePanel = new JPanel(); 
		messagePanel.setBounds(this.popupOwner.getBounds());
		messagePanel.setBackground(SwingApp.BACKGROUND_COLOR);
		messagePanel.setLayout(
				new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
		
		JLabel headingLabel = new JLabel(this.getString("help.message.heading"));
		setHeadingFont(headingLabel);
		messagePanel.add(headingLabel);
		messagePanel.add(new JLabel(" "));

		this.addMessageLabels("help.message.intro", messagePanel);

		ApplicationState state = this.controller.getCurrentState();
		addStateBasedMessage(messagePanel, state);
		messagePanel.add(new JLabel(" "));

		this.addMessageLabels("message.disclaimer", messagePanel);
		
		messagePanel.setBackground(this.popupOwner.getBackground());

		return messagePanel;
	}

	private void addMessageLabels(String messageKey, JPanel parentPanel) {
		String[] decoded = this.getLines(messageKey);
		for(String message : decoded) {
			JLabel label = new JLabel(message);
			parentPanel.add(label);
		}
	}

	/**
	 * @param messagePanel
	 * @param state
	 */
	private void addStateBasedMessage(JPanel parentPanel,
			ApplicationState state) {
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(
				new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
		messagePanel.setBackground(STATE_BASED_MESSAGE_COLOR);
		messagePanel.setBorder(LineBorder.createGrayLineBorder());
		if(state == ApplicationState.EMPTY) {
			this.addMessageLabels("help.message.load", messagePanel);
		} else if(state == ApplicationState.IMAGE_LOADED) {
			this.addMessageLabels("help.message.scramble_or_unscramble", messagePanel);
		} else if(state == ApplicationState.SCRAMBLED) {
			this.addMessageLabels("help.message.save.scrambled", messagePanel);
		} else if(state == ApplicationState.UNSCRAMBLED) {
			this.addMessageLabels("help.message.save.unscrambled", messagePanel);
		}else if(state == ApplicationState.SAVED) {
			this.addMessageLabels("help.message.done", messagePanel);
		}
		
		parentPanel.add(messagePanel);
	}



	/**
	 * @param headingLabel
	 */
	private void setHeadingFont(JLabel headingLabel) {
		Font oldFont = headingLabel.getFont();
		Font headingFont = new Font(oldFont.getName(), Font.BOLD,oldFont.getSize() + 4);
		headingLabel.setFont(headingFont);
	}

}
