package main;

import main.scenes.Ocean;
import main.things.compoundThings.Scene;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class PerformanceTest {
    /**
     * @param numThreads Number of threads to render with
     * @return Time took in nanoseconds
     */
    private static long render(int numThreads) throws IOException, ExecutionException, InterruptedException {
        Scene scene = new Ocean();

        long start = System.nanoTime();
        scene.getCamera().draw(Main.prepareBufferedImage(1024, 1024), numThreads).get();
        long end = System.nanoTime();

        return end - start;
    }


    /**
     * Reports how long it took to render with a number of threads to standard out
     * @param numThreads Number of threads to render with
     */
    private static void renderAndReport(int numThreads) throws IOException, ExecutionException, InterruptedException {
        long time = render(numThreads);

        System.out.printf("Rendered scene using %d threads in %dms%n", numThreads, time / 1000000);
    }

    /**
     * Renders a scene with varying numbers of threads to see which number performs best
     * @param args
     */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        // Render once with 4 threads to warm up the JIT
        render(4);

        renderAndReport(1);
        renderAndReport(2);
        renderAndReport(4);
        renderAndReport(8);
        renderAndReport(16);
        renderAndReport(32);
        renderAndReport(64);
        renderAndReport(128);
        renderAndReport(256);
    }
}
