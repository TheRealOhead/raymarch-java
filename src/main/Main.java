package main;

import main.io.ImageFiles;
import main.gui.Canvas;
import main.math.vectors.Vector2;
import main.rendering.Camera;
import main.scenes.BallInBox;
import main.scenes.Earth;
import main.scenes.MeldBalls;
import main.things.compoundThings.Scene;
import main.scenes.Ocean;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    /**
     * Renders a single scene to a window in the center of the screen
     */
	public static void main(String[] args) throws IOException {
		Scene scene = new MeldBalls();

		Camera camera = scene.getCamera();

		Vector2 size = new Vector2(1024, 1024);
		int threadCount = 64;

		String path;
		if (args.length > 0)
			path = args[0];
		else
			path = "./renders/render.png";
		String formatName;
		if (args.length > 1)
			formatName = args[1];
		else
			formatName = "PNG";

		BufferedImage image = makeImageDisplayer("Render", size.xInt(), size.yInt());
		camera.draw(image, threadCount)
                .whenComplete((string, throwable) -> ImageFiles.saveImageBufferToFile(image, path, formatName));
	}

    /**
     * Creates a window that displays a BufferedImage
     * @param title Title of window
     * @param x Window X pos
     * @param y Window Y pos
     * @param w Window width
     * @param h Window height
     * @return BufferedImage being displayed
     */
	static BufferedImage makeImageDisplayer(String title, int x, int y, int w, int h) {
		JFrame window = new JFrame();
		window.setTitle(title);
		window.setSize(w, h);

		if (x < 0 || y < 0)
			window.setLocationRelativeTo(null);
		else
			window.setLocation(x, y);

		window.setVisible(true);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		BufferedImage image = prepareBufferedImage(w, h);
		Canvas canvas = new Canvas(image);

		window.add(canvas);

		window.setSize(window.getWidth() + 1, window.getHeight());
		window.setSize(window.getWidth() - 1, window.getHeight());

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				window.repaint();
			}
		}, 0L, 1000L / 10L);

		return image;
	}

    /**
     * Creates a window that displays a BufferedImage in the center of the screen
     * @param title Title of window
     * @param w Window width
     * @param h Window height
     * @return BufferedImage being displayed
     */
	public static BufferedImage makeImageDisplayer(String title, int w, int h) {
		return makeImageDisplayer(title, -1, -1, w, h);
	}

    /**
     * Creates a BufferedImage
     * @param width Image width
     * @param height Image height
     * @return Created image
     */
	static BufferedImage prepareBufferedImage(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
}