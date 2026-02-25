package main.io;

import com.github.kokorin.jaffree.ffmpeg.*;
import com.madgag.gif.fmsware.AnimatedGifEncoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class ImageFiles {
	public static void saveImageBufferToFile(BufferedImage image, String path, String format) {
		try {
			File renderFile = new File(path);

			ImageIO.write(image, format, renderFile);

			System.out.printf("Successfully saved image to %s%n", path);
		} catch (IOException e) {
			System.out.printf("Failed to write file to %s%n", path);
			throw new RuntimeException(e);
		}
	}

	public static void saveImageBuffersToGif(Collection<BufferedImage> images, String path, double fps) throws IOException {
		AnimatedGifEncoder encoder = new AnimatedGifEncoder();
		encoder.start(path);
		encoder.setDelay((int) (1000 / fps));
		encoder.setRepeat(0);
		for (BufferedImage frame : images) {
			encoder.addFrame(frame);
		}
		encoder.finish();
		System.out.printf("Successfully saved %s%n", path);
	}

	public static void saveImageBuffersToVideo(Collection<BufferedImage> images, String path, double fps) throws IOException {
		FrameProducer producer = new FrameProducer() {
			@Override
			public List<Stream> produceStreams() {
				return Collections.singletonList(new Stream()
						.setType(Stream.Type.VIDEO)
						.setTimebase(1000L)
						.setWidth(320)
						.setHeight(240)
				);
			}

			@Override
			public Frame produce() {
				return null;
			}
		};
		/*
		FFmpeg.atPath()
				.addInput(FrameInput.withProducer(producer))
				.addOutput(Paths.get(path))
				.execute();
		 */
	}
}
