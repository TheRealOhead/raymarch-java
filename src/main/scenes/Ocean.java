package main.scenes;

import main.materials.*;
import main.math.vectors.Vector3;
import main.optics.Camera;
import main.things.Plane;
import main.things.compoundThings.Scene;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Ocean extends Scene {
	public Ocean() throws IOException {
		this(0);
	}

	public Ocean(int currentFrame) throws IOException {
		super(currentFrame);
		numberOfFrames = 128;

		add(new Plane(
				new Vector3(0, -2, 0),
				Vector3.UP,
				new Water(1, .5, 10, currentFrame)
		));

		setCamera(new Camera(
				Vector3.ZERO,
				Vector3.YAW.scale((double) currentFrame / numberOfFrames),
				1
		));

		setSkyMaterial(new RadialTexture(ImageIO.read(new File("./images/beach.png"))));
	}
}
