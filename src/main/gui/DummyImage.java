package main.gui;

import java.awt.image.BufferedImage;

public final class DummyImage extends BufferedImage {
	public DummyImage() {
		super(1, 1, TYPE_INT_RGB);
	}

	@Override
	public void setRGB(int x, int y, int rgb) {}
}
