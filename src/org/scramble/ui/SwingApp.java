/**
 * 
 */
package org.scramble.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.scramble.ScrambleController;
import org.scramble.ui.action.AboutListener;
import org.scramble.ui.action.CloseListener;
import org.scramble.ui.action.HelpListener;
import org.scramble.ui.action.OpenFileListener;
import org.scramble.ui.action.SaveFileListener;
import org.scramble.ui.action.ScrambleListener;
import org.scramble.ui.action.UnscrambleListener;
import org.scramble.util.I18NMessageCapable;
import org.scramble.util.file.ImageFileFilter;
import org.scramble.util.file.PNGFilter;

/**
 * Main class for Swing UI for Image Scrambler/Unscrambler
 * 
 * @author alan
 * 
 */
public class SwingApp extends I18NMessageCapable {
	private static final int APPLICATION_HEIGHT = 400;

	private static final int APPLICATION_WIDTH = 800;

	public static final Color BACKGROUND_COLOR = new Color(230, 230, 240);
	
	private static final String RESOURCE_BUNDLE_NAME = "messages";
	private JFrame frame;
	private JLabel labelForImage;
	private JLabel labelForText;
	private JScrollPane scroll;
	private final List<Component> emptyList = new ArrayList<Component>();
	private final List<Component> initialOn = new ArrayList<Component>();
	private final List<Component> initialOff = new ArrayList<Component>();
	private final List<Component> fileSavedOff = new ArrayList<Component>();
	private final List<Component> fileLoadedOn = new ArrayList<Component>();
	private final List<Component> fileLoadedOff = new ArrayList<Component>();
	private final List<Component> alteredOn = new ArrayList<Component>();
	private final List<Component> alteredOff = new ArrayList<Component>();
	private JMenuItem openFile;
	private JMenuItem save;
	private JMenuItem scramble;
	private JMenuItem unscramble;
	private JMenuItem exit;
	private JMenuItem about;
	private JMenuItem help;
	private JFileChooser fcOpen;
	private JPanel aboutPopupContents;

	JMenuBar menuBar;

	public SwingApp() {
		super(RESOURCE_BUNDLE_NAME);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final ScrambleController controller = new ScrambleController();
		makeApp(controller).display(APPLICATION_WIDTH, APPLICATION_HEIGHT);
	}

	/**
	 * Factory method
	 * 
	 * @return Constructed SwingApp
	 */
	public static SwingApp makeApp(final ScrambleController controller) {
		final SwingApp swingApp = new SwingApp();
		swingApp.buildApp(controller);
		return swingApp;
	}

	
	private void buildAboutContents() {
		this.aboutPopupContents = new JPanel(); 

		this.aboutPopupContents.setLayout(
				new BoxLayout(this.aboutPopupContents, BoxLayout.Y_AXIS));
		
		this.aboutPopupContents.add(new JLabel(this.getString("menu.item.about.desc")));
		this.aboutPopupContents.add(new JLabel(this.getString("about.text.content")));
		this.aboutPopupContents.add(new JLabel(this.getString("about.text.version")));
		this.aboutPopupContents.add(new JLabel(this.getString("about.text.license")));
		this.aboutPopupContents.add(new JLabel(this.getString("message.disclaimer")));

		this.aboutPopupContents.setBackground(Color.WHITE);
	}
	
	/**
	 * Set up the swing app
	 * @param controller
	 */
	public void buildApp(ScrambleController controller) {
		this.frame = new JFrame(); 

		this.frame.setLayout(null);
		this.frame.getContentPane().setLayout(
				new BoxLayout(this.frame.getContentPane(), BoxLayout.Y_AXIS));
		this.frame.setTitle(this.getString("application.title"));
		this.frame.getContentPane().setBackground(BACKGROUND_COLOR);
		this.frame.setPreferredSize(new Dimension(APPLICATION_WIDTH, APPLICATION_HEIGHT));
		
		this.labelForImage = new JLabel();
		this.labelForText = new JLabel("", SwingConstants.LEADING);
		
		this.setTextToDisplay("no.image", "");
		// labelForText.setText("No image loaded");
		this.setUpScrollPane();

		this.menuBar = new JMenuBar();
		this.menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));

		this.buildAboutContents(); // "About Scramble" popup 
		
		// Create a menu and add it to the menu bar.
		this.setUpFileMenu();
		this.setUpImageMenu();
		this.setUpHelpMenu();

		this.setUpActivationDeactivationLists(this.scramble, this.unscramble);

		final OpenFileListener openListener = new OpenFileListener(controller,
				this.fileLoadedOn, this.emptyList, this.fileLoadedOff,
				this.emptyList, this, this.fcOpen);
		this.openFile.addActionListener(openListener);

		this.setUpSaveFileChooser(controller, this.scramble, this.unscramble,
				this.save, this.emptyList, this.initialOn, this.fileSavedOff,
				this.alteredOn, this.alteredOff);
		
		this.about.addActionListener(new AboutListener(this.frame, this.aboutPopupContents));
		this.help.addActionListener(new HelpListener(this.frame, controller, RESOURCE_BUNDLE_NAME));

		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setJMenuBar(this.menuBar);
	}

	/**
	 * @param openFile
	 * @param save
	 * @param scramble
	 * @param unscramble
	 */
	protected void setUpActivationDeactivationLists(JMenuItem scramble,
			JMenuItem unscramble) {
		// lists to activate or deactivate
		this.initialOn.add(this.openFile);
		this.initialOn.add(this.about);
		this.initialOff.add(scramble);
		this.initialOff.add(unscramble);
		this.initialOff.add(this.save);
		this.fileSavedOff.add(this.save);

		this.fileLoadedOn.add(scramble);
		this.fileLoadedOn.add(unscramble);
		this.fileLoadedOn.add(this.openFile);
		this.fileLoadedOn.add(this.about);

		this.alteredOn.add(this.save);
		this.alteredOn.add(this.openFile);
		this.alteredOn.add(this.about);

	}

	/**
	 * @return
	 */
	protected JMenu setUpImageMenu() {
		final JMenu imageMenu = new JMenu(this.getString("menu.image"));
		imageMenu.setMnemonic(KeyEvent.VK_I);
		imageMenu.getAccessibleContext().setAccessibleDescription(
				"Image operations");
		this.menuBar.add(imageMenu);

		this.scramble = this.makeAndAddMenuItem(imageMenu, KeyEvent.VK_R,
				"menu.item.scramble", "menu.item.scramble.desc", false);

		this.unscramble = this.makeAndAddMenuItem(imageMenu, KeyEvent.VK_U,
				"menu.item.unscramble", "menu.item.unscramble.desc", false);

		return imageMenu;
	}
	
	/**
	 * @return
	 */
	protected JMenu setUpHelpMenu() {
		final JMenu helpMenu = new JMenu(this.getString("menu.help"));
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.getAccessibleContext().setAccessibleDescription(
				"Help"); // TODO : I18N
		this.menuBar.add(helpMenu);

		this.about = this.makeAndAddMenuItem(helpMenu, KeyEvent.VK_A,
				"menu.item.about", "menu.item.about.desc", true);
		
		this.help = this.makeAndAddMenuItem(helpMenu, KeyEvent.VK_H, "menu.item.help", "menu.item.help.desc", true);

		return helpMenu;
	}

	/**
	 * Set up the file menu and its items
	 * @return The top-level menu item
	 */
	protected JMenu setUpFileMenu() {
		final JMenu fileMenu = new JMenu(this.getString("menu.file"));
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.getAccessibleContext().setAccessibleDescription(
				"File operations");
		this.menuBar.add(fileMenu);

		this.openFile = this.makeAndAddMenuItem(fileMenu,
				KeyEvent.VK_O, "menu.item.open", "menu.item.open.desc", true);
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));

		this.fcOpen = this.makeOpenFileChooser();

		this.save = this.makeAndAddMenuItem(fileMenu, KeyEvent.VK_S,
				"menu.item.save", "menu.item.save.desc", false);

		this.exit = this.makeAndAddMenuItem(fileMenu, KeyEvent.VK_X,
				"menu.item.exit", "menu.item.exit.desc", true);

		this.exit.addActionListener(new CloseListener(this.frame));

		return fileMenu;
	}

	/**
	 * @return File chooser dialog for opening files
	 */
	protected JFileChooser makeOpenFileChooser() {
		final JFileChooser fcOpen = new JFileChooser();
		fcOpen.setDialogTitle(this.getString("open.dialog.title"));
		fcOpen.addChoosableFileFilter(new ImageFileFilter(this));
		fcOpen.setApproveButtonText(this.getString("button.open"));
		return fcOpen;
	}

	/**
	 * @param controller
	 * @param scramble
	 * @param unscramble
	 * @param save
	 * @param emptyList
	 * @param initialOn
	 * @param fileSavedOff
	 * @param alteredOn
	 * @param alteredOff
	 */
	protected void setUpSaveFileChooser(ScrambleController controller,
			JMenuItem scramble, JMenuItem unscramble, JMenuItem save,
			List<Component> emptyList, List<Component> initialOn,
			List<Component> fileSavedOff, List<Component> alteredOn,
			List<Component> alteredOff) {
		final JFileChooser fcSaveScrambled = new JFileChooser();
		final JFileChooser fcSaveDefault = new JFileChooser();

		fcSaveScrambled.setDialogTitle(this.getString("save.dialog.title"));
		fcSaveDefault.setDialogTitle(this.getString("save.dialog.title"));

		fcSaveScrambled.setApproveButtonText(this.getString("save.dialog.button.label"));
		fcSaveDefault.setApproveButtonText(this.getString("save.dialog.button.label"));

		final SaveFileListener saveListener = new SaveFileListener(controller, 
				initialOn, emptyList, fileSavedOff, emptyList, this, fcSaveDefault, fcSaveScrambled);

		save.addActionListener(saveListener);
		fcSaveScrambled.addChoosableFileFilter(new PNGFilter(this));
		fcSaveDefault.addChoosableFileFilter(new ImageFileFilter(this));

		scramble.addActionListener(new ScrambleListener(controller, alteredOn,
				emptyList, alteredOff, emptyList, this, this.frame, this
						.getTranslator()));
		unscramble.addActionListener(new UnscrambleListener(controller,
				alteredOn, emptyList, alteredOff, emptyList, this, this.frame,
				this.getTranslator()));
	}

	/**
	 * Create and add the scroll pane
	 */
	protected void setUpScrollPane() {
		this.scroll = new JScrollPane(this.labelForImage);
		this.frame.add(this.scroll);
		this.frame.add(this.labelForText);
		this.scroll.getViewport().setBackground(BACKGROUND_COLOR);
		this.scroll.setPreferredSize(new Dimension(APPLICATION_WIDTH + 10, APPLICATION_HEIGHT));
	}

	/**
	 * @param width
	 * @param height
	 */
	public void display(int width, int height) {
		this.frame.setSize(width, height); // Show the frame

		this.frame.setVisible(true);
	}

	/**
	 * Make a menu item
	 * 
	 * @param parentMenu
	 * @param keyEvent
	 * @param menuItemTitle
	 * @param menuItemDescription
	 * @param initiallyEnabled
	 * @return
	 */
	private JMenuItem makeAndAddMenuItem(JMenu parentMenu, int keyEvent,
			String menuItemTitle, String menuItemDescription,
			boolean initiallyEnabled) {
		final JMenuItem scramble = new JMenuItem(this.getString(menuItemTitle),
				keyEvent);
		scramble.setAccelerator(KeyStroke.getKeyStroke(keyEvent,
				ActionEvent.CTRL_MASK));

		scramble.getAccessibleContext().setAccessibleDescription(
				this.getString(menuItemDescription));
		scramble.setEnabled(initiallyEnabled);
		parentMenu.add(scramble);
		return scramble;
	}

	/**
	 * Resize the frame
	 * 
	 * @param targetWidth
	 * @param targetHeight
	 */
	public void resizeFrame(final int targetWidth, final int targetHeight) {
		Container c = this.frame;
		while (null != c.getParent()) {
			c = c.getParent();
		}
		c.setSize(targetWidth, targetHeight);
	}

	/**
	 * Force a redraw of the main frame
	 */
	public void forceRedraw() {
		this.labelForImage.invalidate();
		this.scroll.paintImmediately(this.scroll.getBounds());
		this.frame.validate();
	}

	/**
	 * @return The main frame
	 */
	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Show text
	 * 
	 * @param message
	 */
	public void setTextToDisplay(String message, String postfix) {
		this.labelForText.setText(this.getString(message) + postfix); // "Image loaded : "
																		// +
																		// fileToOpen.getAbsolutePath()

		// this.feedbackLabel.setIcon(new
		// ImageIcon(this.controller.getCurrentImage()));
	}

	/**
	 * Show image
	 * 
	 * @param image
	 */
	public void setImageToDisplay(Image image) {
		this.labelForImage.setIcon(new ImageIcon(image));
	}
}
