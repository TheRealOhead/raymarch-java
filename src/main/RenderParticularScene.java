package main;

import main.io.ImageFiles;
import main.things.compoundThings.Scene;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

public class RenderParticularScene {
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		Class<?> sceneClazz = Class.forName(args[0]);
		Scene scene = (Scene) sceneClazz.getConstructor().newInstance();

		BufferedImage image = Main.prepareBufferedImage(2048, 2048);
		scene.getCamera().draw(image, 32)
                .thenRun(() -> ImageFiles.saveImageBufferToFile(image, "./renders/" + args[0] + ".png", "PNG"));
	}
}
