package swing;

import javax.swing.*;

/**
 * extension of JFrameTemplate class with some additional settings
 */
public class JFrameTemplate extends JFrame {
	/**
	 * creates JFrameTemplate
	 * @param title title of the window
	 * @param width width of the window
	 * @param height height of the window
	 * @param icon icon of the window
	 * @param exitOnClose pass `true` if you want exit on window close, else pass `false`
	 */
	public JFrameTemplate(String title, int width, int height, ImageIcon icon, boolean exitOnClose) {
		super(title);

		this.setIconImage(icon.getImage());
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		if (exitOnClose) {
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}

	/**
	 * creates JFrameTemplate with 'exit on window close' behavior
	 * @param title title of the window
	 * @param width width of the window
	 * @param height height of the window
	 * @param icon icon of the window
	 */
	public JFrameTemplate(String title, int width, int height, ImageIcon icon) {
		this(title, width, height, icon, true);
	}
}
