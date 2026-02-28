package main;

import main.io.ImageFiles;
import main.scenes.Earth;
import main.scenes.MeldBalls;
import main.things.compoundThings.Scene;
import main.scenes.Ocean;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class SaveGIF {
	private static class FrameTracker {
		private volatile boolean active = true;
		public void deactivate() {
			active = false;
		}
		public boolean isActive() {
			return active;
		}
	}

	public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Scene scene = Main.getDefaultScene(0);

        long start = System.nanoTime();

		int totalThreads = 32;
		List<BufferedImage> images = new LinkedList<>();
		for (int i = 0; i < scene.getNumberOfFrames(); i++) {
			FrameTracker frameTracker = new FrameTracker();
			scene = Main.getDefaultScene(i);
			BufferedImage image = Main.prepareBufferedImage(256, 256);
			images.add(image);

			Scene finalScene = scene;
			int finalI = i;
			scene.getCamera().draw(image, totalThreads).thenRun(()->{
				frameTracker.deactivate();
				System.out.printf("Frame %d of %d complete%n", finalI + 1, finalScene.getNumberOfFrames());
			});

			while (frameTracker.isActive())
				Thread.onSpinWait();
		}

        long end = System.nanoTime();

        System.out.printf("Entire render took %dms%n", (end - start) / 1000000);

		ImageFiles.saveImageBuffersToGif(images, "renders/" + scene.getClass().getSimpleName() + ".gif", 15);
	}
}
