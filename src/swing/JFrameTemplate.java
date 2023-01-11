package src.swing;

import javax.swing.*;

public class JFrameTemplate extends JFrame {
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

	public JFrameTemplate(String title, int width, int height, ImageIcon icon) {
		this(title, width, height, icon, true);
	}
}
