package src.swing;

import javax.swing.*;
import java.awt.*;

public class JTextFieldTemplate extends JTextField {
	private final static int DEFAULT_FONT_SIZE = 16;
	private final static int DEFAULT_TEXT_ALIGNMENT = JTextField.RIGHT;
	public JTextFieldTemplate(String text) {
		this(text, DEFAULT_FONT_SIZE, DEFAULT_TEXT_ALIGNMENT);
	}

	public JTextFieldTemplate(String text, int fontSize, int textAlignment) {
		super();

		this.setFont(new Font("Arial", Font.TYPE1_FONT, fontSize));
		this.setHorizontalAlignment(textAlignment);
		this.setEditable(false);
//		this.setBackground(Color.green); // TODO: debug
		this.setBorder(null);

		this.setBorder(BorderFactory.createCompoundBorder(
				  this.getBorder(),
				  BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		this.setText(text);
	}
}
