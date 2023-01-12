package swing;

import javax.swing.*;
import java.awt.*;

/**
 * extension of JTextFieldTemplate class with some additional settings
 */
public class JTextFieldTemplate extends JTextField {
	/**
	 * default font size on JTextField
	 */

	private final static int DEFAULT_FONT_SIZE = 16;

	/**
	 * default text alignment on JTextField
	 */
	private final static int DEFAULT_TEXT_ALIGNMENT = JTextField.CENTER;

	/**
	 *
	 * @param text text to be displayed on JTextField
	 */
	public JTextFieldTemplate(String text) {
		this(text, DEFAULT_FONT_SIZE, DEFAULT_TEXT_ALIGNMENT);
	}

	/**
	 *
	 * @param text text to be displayed on JTextField
	 * @param fontSize font size of text
	 * @param textAlignment text alignment
	 */
	public JTextFieldTemplate(String text, int fontSize, int textAlignment) {
		this(text, fontSize, textAlignment, true);
	}

	/**
	 *
	 * @param text text to be displayed on JTextField
	 * @param fontSize font size of text
	 * @param textAlignment text alignment
	 * @param noBorder pass `true` if you want JTextField without border, else pass `false`
`	 */
	public JTextFieldTemplate(String text, int fontSize, int textAlignment, boolean noBorder) {
		super();

		this.setFont(new Font("Arial", Font.BOLD, fontSize));
		this.setHorizontalAlignment(textAlignment);
		this.setEditable(false);
//		this.setBackground(Color.green); // TODO: debug

		if (noBorder) {
			this.setBorder(null);
		}

		this.setBorder(BorderFactory.createCompoundBorder(
				  this.getBorder(),
				  BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		this.setText(text);
	}
}
