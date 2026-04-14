package main.scenes;

import main.materials.*;
import main.math.vectors.Vector3;
import main.rendering.Camera;
import main.things.Plane;
import main.things.compoundThings.Scene;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ocean extends Scene {
    private static BufferedImage skybox;

    static {
        try {
            skybox = ImageIO.read(new File("./images/beach.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	public Ocean(int frameNumber) {
		super(frameNumber);
	}

	@Override
	public int getNumberOfFrames() {
		return 128;
	}

	@Override
	public void build(int frameNumber) {

		add(new Plane(
				new Vector3(0, -2, 0),
                Vector3.ZERO,
				Vector3.UP,
				new Water(1, .5, 10, frameNumber)
		));

		setCamera(new Camera(
				Vector3.ZERO,
				Vector3.YAW.scale((double) frameNumber / getNumberOfFrames()),
				1
		));

		setSkyMaterial(new RadialTexture(skybox));
	}
}
