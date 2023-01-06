package src.swing;

import javax.swing.*;
import java.awt.*;

public class JButtonTemplate extends JButton {
	public JButtonTemplate(String text) {
		super(text);

		this.setFont(new Font("Arial", Font.TYPE1_FONT, 16));
		this.setBorder(null);
	}

	public JButtonTemplate(String text, Rectangle bounds) {
		this(text);

		this.setBounds(bounds);
	}
}
