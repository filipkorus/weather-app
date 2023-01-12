package swing;

import javax.swing.*;
import java.awt.*;

/**
 * extension of JButton class with some additional settings
 */
public class JButtonTemplate extends JButton {
	/**
	 *
	 * @param text text to be displayed on the button
	 */
	public JButtonTemplate(String text) {
		super(text);

		this.setFont(new Font("Bank Gothic", Font.BOLD, 16));
		this.setBorder(null);
	}

	/**
	 *
	 * @param text text to be displayed on the button
	 * @param bounds bounds of the button
	 */
	public JButtonTemplate(String text, Rectangle bounds) {
		this(text);

		this.setBounds(bounds);
	}
}
