import main.io.ImageFiles;
import main.gui.Canvas;
import main.math.vectors.Vector2;
import main.optics.Camera;
import main.things.compoundThings.Scene;
import scenes.Ocean;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

	public static void main(String[] args) throws IOException {
		Scene scene = new Ocean();

		Camera camera = scene.getCamera();

		Vector2 size = new Vector2(1024, 1024);
		int threadCount = 32;

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
		camera.draw(image, threadCount, () -> ImageFiles.saveImageBufferToFile(image, path, formatName));
	}

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

	private static BufferedImage makeImageDisplayer(String title, int w, int h) {
		return makeImageDisplayer(title, -1, -1, w, h);
	}

	static BufferedImage prepareBufferedImage(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}
}