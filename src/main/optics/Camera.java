package main.optics;

import main.materials.MaterialData;
import main.math.vectors.Vector2;
import main.math.vectors.Vector3;
import main.things.compoundThings.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Camera {
	Vector3 position;
	Vector3 rotation;
	double focalLength;
	Scene scene = null;

	public Camera(Vector3 position, Vector3 rotation, double focalLength) {
		this.position = position;
		this.rotation = rotation;
		this.focalLength = focalLength;
	}

	public void setWorld(Scene scene) {
		this.scene = scene;
	}

	private FragmentData captureFragment(Vector2 uv) {

		Vector3 direction = uv.toVector3(focalLength);

		direction = direction.rotate(rotation);

		Ray ray = new Ray(position, direction, scene);

		FragmentData data = new FragmentData();

		data.color = ray.march(Ray.RECURSION_COUNT);

		MaterialData materialData = ray.materialData;
		data.albedo = materialData.albedo();
		data.depth = new Vector3(1 / ray.getDepth()).asColor();
		data.normal = ray.getWorldNormal(materialData).add(Vector3.ONE).scale(.5).asColor();
		data.stepCount = new Vector3(1 - ray.getStepsTaken() / Ray.MAX_STEPS).asColor();
		data.normalModifications = ray.materialData.normalModifier().add(Vector3.ONE).scale(.5).asColor();

		return data;
	}

	private FragmentData getDataAt(Vector2 screenPosition, Vector2 size) {
		Vector2 uv = screenPosition.scale(1 / (size.x)).subtract(new Vector3(.5)).toVector2();

		uv = new Vector2(uv.x, -uv.y);

		return captureFragment(uv);
	}

	/**
	 * A generic interface for drawing pixels to something. Could be an image buffer, a graphics context, whatever
	 */
	private interface PixelWriter {
		void run(FragmentData data, int x, int y);
	}

	private static class FragmentJob {
		Vector2 location;
		Vector2 size;
		FragmentJob(Vector2 location, Vector2 size) {
			this.location = location;
			this.size = size;
		}
	}

	private static class FragmentData {
		Color color;
		Color albedo;
		Color depth;
		Color normal;
		Color thread;
		Color complexity;
		Color stepCount;
		Color normalModifications;
	}

	/**
	 * Takes a set of coordinates that has yet to be rendered, and renders it!
	 */
	private class FragmentWorker extends Thread {
		private final int totalFragments;
		private final ConcurrentLinkedDeque<FragmentJob> queue;
		private final PixelWriter writer;
		private static int numberOfThreads;
		private final int id;
		private final Color debugColor = new Color((int) (Math.random() * 0xFFFFFF));

		public FragmentWorker(String name, ConcurrentLinkedDeque<FragmentJob> fragmentQueue, PixelWriter pixelWriter) {
			super(name);

			this.queue = fragmentQueue;
			this.writer = pixelWriter;

			id = numberOfThreads;
			numberOfThreads++;

			this.totalFragments = queue.size();
		}

		@Override
		public void run() {
			// As long as there are fragments to render, keep going
			while (!queue.isEmpty()) {
				FragmentJob job;
				try {
					job = queue.pop();
				} catch (NoSuchElementException e) {
					// Someone else got to it first. That was fast!
					return;
				}

				long startTime = System.nanoTime();
				FragmentData data = getDataAt(job.location, job.size);
				long endTime = System.nanoTime();

				double timeTook = endTime - startTime;

				data.thread = this.debugColor;
				data.complexity = new Vector3(10000 / timeTook).asColor();
				writer.run(
						data,
						(int) job.location.x,
						(int) job.location.y
				);
			}
		}
	}

	private void forEachPixel(Vector2 size, PixelWriter pixelWriter, int threadCount, Runnable runWhenDone) {
		ArrayList<FragmentJob> jobsArrayList = new ArrayList<>();

		/*
		// Add fragments by powers of two
		int totalFragments = size.xInt() * size.yInt();
		int powerOfTwo = (int) Math.ceil(Math.log(totalFragments) / Math.log(2));
		while (powerOfTwo >= 0) {
			for (int i = 0; i < totalFragments; i += (int) Math.pow(2, powerOfTwo)) {
				jobsArrayList.add(new FragmentJob(new Vector2(i / size.xInt(), i % size.xInt()), size));
			}
			powerOfTwo--;
		}
		*/

		/*
		// Add pixels from left to right
		for (int x = 0; x < size.x; x++) {
			for (int y = 0; y < size.y; y++) {
				jobsArrayList.add(new FragmentJob(
						new Vector2(x, y),
						size
				));
			}
		}
		*/


		// Add pixels from top to bottom
		for (int y = 0; y < size.y; y++) {
			for (int x = 0; x < size.x; x++) {
				jobsArrayList.add(new FragmentJob(
						new Vector2(x, y),
						size
				));
			}
		}





		// Shuffle fragments in queue
		for (int i = 0; i < jobsArrayList.size(); i++) {
			FragmentJob temp = jobsArrayList.get(i);
			int random = (int) (Math.random() * jobsArrayList.size());
			jobsArrayList.set(i, jobsArrayList.get(random));
			jobsArrayList.set(random, temp);
		}



		ConcurrentLinkedDeque<FragmentJob> jobQueue = new ConcurrentLinkedDeque<>(jobsArrayList);

		for (int i = 0; i < threadCount; i++) {
			new FragmentWorker("Fragment-Renderer-" + (i + 1), jobQueue, pixelWriter).start();
		}

		Thread overseer = new Thread("Overseer") {
			@Override
			public void run() {
				long start = System.nanoTime() / 1_000_000L;
				waitForWorkersToFinish();
				long end = System.nanoTime() / 1_000_000L;
				System.out.printf("Completed render in %dms%n", end - start);
				if (runWhenDone != null)
					runWhenDone.run();
			}

			private void waitForWorkersToFinish() {
				while (!jobQueue.isEmpty()) {
					//System.out.println(jobQueue.size());
					Thread.onSpinWait();
				}
			}
		};

		overseer.start();
	}


	/**
	 * Draws the camera's perspective to a swing component
	 * @param g Graphics context
	 * @param component JComponent to draw to
	 * @param threadCount How many threads to use when drawing
	 */
	public void draw(Graphics g, JComponent component, int threadCount, Runnable runWhenDone) {
		Vector2 size = new Vector2(component.getWidth(), component.getHeight());

		forEachPixel(size, (data, x, y) -> {
			g.setColor(data.color);
			g.fillRect(x, y, 1, 1);
		}, threadCount, runWhenDone);
	}


	/**
	 * Draws the camera's perspective to a BufferedImage
	 * @param bufferedImage Image to draw to
	 * @param threadCount How many threads to use when drawing
	 */
	public void draw(BufferedImage bufferedImage, int threadCount, Runnable runWhenDone) {
		Vector2 size = new Vector2(bufferedImage.getWidth(), bufferedImage.getHeight());
		forEachPixel(size, (data, x, y) -> bufferedImage.setRGB(x, y, data.color.getRGB()), threadCount, runWhenDone);
	}

	/**
	 * Outputs a lot of debug values to 6 different windows
	 * @param product The normal output of the render
	 * @param depth Depth value for each fragment
	 * @param normals Normal for each fragment
	 * @param threads Color coded which thread rendered this fragment
	 * @param complexity How long it took to compute this fragment
	 * @param stepCount How many marches it took to compute this fragment
	 * @param threadCount How many threads to use
	 */
	public void debugViews(BufferedImage product,
						   BufferedImage albedo,
						   BufferedImage depth,
						   BufferedImage normals,
						   BufferedImage threads,
						   BufferedImage complexity,
						   BufferedImage stepCount,
						   BufferedImage normalModifications,
						   int threadCount,
						   Runnable runWhenDone) {
		Vector2 size = new Vector2(product.getWidth(), product.getHeight());

		forEachPixel(size,
				(data, x, y) -> {
					product.setRGB(x, y, data.color.getRGB());
					albedo.setRGB(x, y, data.albedo.getRGB());
					depth.setRGB(x, y, data.depth.getRGB());
					normals.setRGB(x, y, data.normal.getRGB());
					threads.setRGB(x, y, data.thread.getRGB());
					complexity.setRGB(x, y, data.complexity.getRGB());
					stepCount.setRGB(x, y, data.stepCount.getRGB());
					normalModifications.setRGB(x, y, data.normalModifications.getRGB());
				},
				threadCount, runWhenDone);
	}
}
