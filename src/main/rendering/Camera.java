package main.rendering;

import main.materials.MaterialData;
import main.math.VarArgsMath;
import main.math.vectors.Vector2;
import main.math.vectors.Vector3;
import main.things.compoundThings.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
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

		Color resultColor = ray.march(Ray.RECURSION_COUNT);
        MaterialData materialData = ray.materialData;

        return new FragmentData(
                resultColor,
                materialData.albedo(),
                new Vector3(1 / ray.getDepth()).asColor(),
                ray.getWorldNormal(materialData).add(Vector3.ONE).scale(.5).asColor(),
                null,
                null,
                new Vector3(1 - ray.getStepsTaken() / Ray.MAX_STEPS).asColor(),
                ray.materialData.normalModifier().add(Vector3.ONE).scale(.5).asColor()
        );
	}

	FragmentData getDataAt(Vector2 screenPosition, Vector2 size) {
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

                data = new FragmentData(
                        data.color(),
                        data.albedo(),
                        data.depth(),
                        data.normal(),
                        this.debugColor,
                        new Vector3(10000 / timeTook).asColor(),
                        data.stepCount(),
                        data.normalModifications()
                );

				writer.run(
						data,
						(int) job.location.x,
						(int) job.location.y
				);
			}
		}
	}

	private void addPixelsByPowersOf2(ArrayList<FragmentJob> jobsArrayList, Vector2 size) {
		int totalFragments = size.xInt() * size.yInt();
		int powerOfTwo = (int) Math.ceil(Math.log(totalFragments) / Math.log(2));
		while (powerOfTwo >= 0) {
			for (int i = 0; i < totalFragments; i += (int) Math.pow(2, powerOfTwo)) {
				int x = i / size.xInt();
				int y = i % size.yInt();
				addJob(jobsArrayList, x, y, size);
			}
			powerOfTwo--;
		}
	}

	private ArrayList<FragmentJob> getPixelsByDivideAndConquer(Vector2 size, int x, int y, int s) {
		ArrayList<FragmentJob> list = new ArrayList<>();

		if (s > 1) {
			int halfS = s / 2;

			list.addAll(getPixelsByDivideAndConquer(size, x, y, halfS)); // Top left
			list.addAll(getPixelsByDivideAndConquer(size, x + halfS, y, halfS)); // Top right
			list.addAll(getPixelsByDivideAndConquer(size, x, y + halfS, halfS)); // Bottom left
			list.addAll(getPixelsByDivideAndConquer(size, x + halfS, y + halfS, halfS)); // Bottom right
		}

		if (s <= 1 && x < size.xInt() && y < size.yInt())
			list.add(new FragmentJob(new Vector2(x, y), size));

		return list;
	}

	private void addPixelsByDivideAndConquer(ArrayList<FragmentJob> jobsArrayList, Vector2 size) {
		jobsArrayList.addAll(getPixelsByDivideAndConquer(size, 0, 0, Math.max(
					(int) Math.pow(2, Math.ceil(VarArgsMath.log2(size.xInt()))),
					(int) Math.pow(2, Math.ceil(VarArgsMath.log2(size.yInt())))
				)
		));
	}

	private void addPixelsLeftToRight(ArrayList<FragmentJob> jobsArrayList, Vector2 size) {
		for (int x = 0; x < size.x; x++) {
			for (int y = 0; y < size.y; y++) {
				addJob(jobsArrayList, x, y, size);
			}
		}
	}

	private void addPixelsFromTopToBottom(ArrayList<FragmentJob> jobsArrayList, Vector2 size) {
		for (int y = 0; y < size.y; y++) {
			for (int x = 0; x < size.x; x++) {
				addJob(jobsArrayList, x, y, size);
			}
		}
	}

	private void addPixelsShuffled(ArrayList<FragmentJob> jobsArrayList, Vector2 size) {
		addPixelsFromTopToBottom(jobsArrayList, size);
		for (int i = 0; i < jobsArrayList.size(); i++) {
			FragmentJob temp = jobsArrayList.get(i);
			int random = (int) (Math.random() * jobsArrayList.size());
			jobsArrayList.set(i, jobsArrayList.get(random));
			jobsArrayList.set(random, temp);
		}
	}

	private void addPixelsSpiral(ArrayList<FragmentJob> jobsArrayList, Vector2 size) {
		int x = 0;
		int y = 0;

		for (int i = 0; i < size.xInt() * size.yInt(); i++) {
			// Right
			for (; x < size.xInt() - i; x++) {
				addJob(jobsArrayList, x, y, size);
			}
			x--;

			// Down
			for (; y < size.yInt() - i; y++) {
				addJob(jobsArrayList, x, y, size);
			}
			y--;

			// Left
			for (; x >= i; x--) {
				addJob(jobsArrayList, x, y, size);
			}
			x++;

			// Up
			for (; y >= i + 1; y--) {
				addJob(jobsArrayList, x, y, size);
			}
			y++;
		}
	}

	private void addPixelsSpiralInverse(ArrayList<FragmentJob> jobsArrayList, Vector2 size) {
		addPixelsSpiral(jobsArrayList, size);
		ArrayList<FragmentJob> reversed = new ArrayList<>(jobsArrayList.reversed());
		jobsArrayList.clear();
		jobsArrayList.addAll(reversed);
	}

	private void addJob(ArrayList<FragmentJob> jobsArrayList, int x, int y, Vector2 size) {
		jobsArrayList.add(new FragmentJob(
				new Vector2(x, y),
				size
		));
	}

	private CompletableFuture<Void> forEachPixel(Vector2 size, PixelWriter pixelWriter, int threadCount) {
		return CompletableFuture.runAsync(() -> {
            ArrayList<FragmentJob> jobsArrayList = new ArrayList<>();

            addPixelsByDivideAndConquer(jobsArrayList, size);

            ConcurrentLinkedDeque<FragmentJob> jobQueue = new ConcurrentLinkedDeque<>(jobsArrayList);

            for (int i = 0; i < threadCount; i++) {
                new FragmentWorker("Fragment-Renderer-" + (i + 1), jobQueue, pixelWriter).start();
            }


            long start = System.nanoTime() / 1_000_000L;
            while (!jobQueue.isEmpty())
                Thread.onSpinWait();
            long end = System.nanoTime() / 1_000_000L;
            System.out.printf("Completed render in %dms%n", end - start);
        });
    }


	/**
     * Draws the camera's perspective to a swing component
     *
     * @param g           Graphics context
     * @param component   JComponent to draw to
     * @param threadCount How many threads to use when drawing
     * @return
     */
	public CompletableFuture<Void> draw(Graphics g, JComponent component, int threadCount) {
		Vector2 size = new Vector2(component.getWidth(), component.getHeight());

		return forEachPixel(size, (data, x, y) -> {
			g.setColor(data.color());
			g.fillRect(x, y, 1, 1);
		}, threadCount);
	}


	/**
	 * Draws the camera's perspective to a BufferedImage
	 * @param bufferedImage Image to draw to
	 * @param threadCount How many threads to use when drawing
	 */
	public CompletableFuture<Void> draw(BufferedImage bufferedImage, int threadCount) {
		Vector2 size = new Vector2(bufferedImage.getWidth(), bufferedImage.getHeight());
		return forEachPixel(size, (data, x, y) -> bufferedImage.setRGB(x, y, data.color().getRGB()), threadCount);
	}

	/**
     * Outputs a lot of debug values to 6 different windows
     *
     * @param product     The normal output of the render
     * @param depth       Depth value for each fragment
     * @param normals     Normal for each fragment
     * @param threads     Color coded which thread rendered this fragment
     * @param complexity  How long it took to compute this fragment
     * @param stepCount   How many marches it took to compute this fragment
     * @param threadCount How many threads to use
     * @return
     */
	public CompletableFuture<Void> debugViews(BufferedImage product,
                                                BufferedImage albedo,
                                                BufferedImage depth,
                                                BufferedImage normals,
                                                BufferedImage threads,
                                                BufferedImage complexity,
                                                BufferedImage stepCount,
                                                BufferedImage normalModifications,
                                                int threadCount) {
		Vector2 size = new Vector2(product.getWidth(), product.getHeight());

		return forEachPixel(size,
				(data, x, y) -> {
					product.setRGB(x, y, data.color().getRGB());
					albedo.setRGB(x, y, data.albedo().getRGB());
					depth.setRGB(x, y, data.depth().getRGB());
					normals.setRGB(x, y, data.normal().getRGB());
					threads.setRGB(x, y, data.thread().getRGB());
					complexity.setRGB(x, y, data.complexity().getRGB());
					stepCount.setRGB(x, y, data.stepCount().getRGB());
					normalModifications.setRGB(x, y, data.normalModifications().getRGB());
				},
				threadCount);
	}
}
