package main.rendering;

import main.math.vectors.Vector3;

import java.awt.*;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Takes a set of coordinates that has yet to be rendered, and renders it!
 */
class FragmentWorker extends Thread {
    private final Camera camera;
    private final int totalFragments;
    private final ConcurrentLinkedDeque<FragmentJob> queue;
    private final PixelWriter writer;
    private static int numberOfWorkerThreads;
    private final int id;
    private final Color debugColor = new Color((int) (Math.random() * 0xFFFFFF));

    public FragmentWorker(Camera camera, String name, ConcurrentLinkedDeque<FragmentJob> fragmentQueue, PixelWriter pixelWriter) {
        super(name);
        this.camera = camera;

        this.queue = fragmentQueue;
        this.writer = pixelWriter;

        id = numberOfWorkerThreads;
        numberOfWorkerThreads++;

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

            render(camera, job, writer, debugColor);
        }
    }

    static void render(Camera camera, FragmentJob job, PixelWriter writer, Color debugColor) {
        long startTime = System.nanoTime();
        FragmentData data = camera.getDataAt(job.location(), job.size());
        long endTime = System.nanoTime();

        double timeTook = endTime - startTime;

        data = new FragmentData(
                data.color(),
                data.albedo(),
                data.depth(),
                data.normal(),
                debugColor,
                new Vector3(10000 / timeTook).asColor(),
                data.stepCount(),
                data.normalModifications()
        );

        writer.run(
                data,
                (int) job.location().x,
                (int) job.location().y
        );
    }
}
