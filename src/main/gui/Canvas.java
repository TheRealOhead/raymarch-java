package main.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas extends JPanel {
	private BufferedImage image;

	public Canvas(BufferedImage image) {
		super();
		this.image = image;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.drawImage(image, 0, 0, null);

	}


}
