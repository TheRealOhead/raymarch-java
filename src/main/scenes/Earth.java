package main.scenes;

import main.materials.RadialTexture;
import main.materials.SolidColor;
import main.math.vectors.Vector2;
import main.math.vectors.Vector3;
import main.optics.Camera;
import main.optics.DirectionalLightSource;
import main.things.Sphere;
import main.things.compoundThings.Scene;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Earth extends Scene {
	BufferedImage earthImage;

	@Override
	public int getNumberOfFrames() {
		return 16;
	}

	public Earth(int frameNumber) throws IOException {

		earthImage = ImageIO.read(new File("./images/earth.jpg"));

		add(new Sphere(
				new Vector3(0, 0, 5),
				1,
				new RadialTexture(earthImage, new Vector2((Math.PI * 2 * frameNumber / getNumberOfFrames()), 0))
		));

		setCamera(new Camera(
				Vector3.ZERO,
				Vector3.ZERO,
				1
		));

		setAmbientLight(new Color(19, 19, 19));
		setDirectionalLight(new DirectionalLightSource(
				Color.WHITE,
				new Vector3(.5, -1, .5)
		));

		setSkyMaterial(new SolidColor(Color.BLACK));
	}
}
