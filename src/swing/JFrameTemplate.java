package src.swing;

import javax.swing.*;

public class JFrameTemplate extends JFrame {
	private final static int DEFAULT_WIDTH = 420;
	private final static int DEFAULT_HEIGHT = 550;
	public JFrameTemplate(String title, int width, int height, ImageIcon icon) {
		super(title);

		this.setIconImage(icon.getImage());
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JFrameTemplate(String title, int width, int height) {
		super(title);

		this.setResizable(false);
		this.setLayout(null);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JFrameTemplate(String title) {
		super(title);

		this.setResizable(false);
		this.setLayout(null);
		this.setSize(this.DEFAULT_WIDTH, this.DEFAULT_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
