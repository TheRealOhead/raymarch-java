package main.rendering;

/**
 * A generic interface for drawing pixels to something. Could be an image buffer, a graphics context, whatever
 */
interface PixelWriter {
    void run(FragmentData data, int x, int y);
}
