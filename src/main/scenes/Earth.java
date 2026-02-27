package main.scenes;

import main.materials.RadialTexture;
import main.materials.SolidColor;
import main.math.vectors.Vector2;
import main.math.vectors.Vector3;
import main.rendering.Camera;
import main.optics.DirectionalLightSource;
import main.things.Sphere;
import main.things.compoundThings.Scene;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Earth extends Scene {
	private static final BufferedImage earthImage;

    static {
        try {
            earthImage = ImageIO.read(new File("./images/earth.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
	public int getNumberOfFrames() {
		return 32;
	}

    public Earth() throws IOException {
        this(0);
    }

	public Earth(int frameNumber) throws IOException {

		add(new Sphere(
				new Vector3(0, 0, 5),
				1,
				new RadialTexture(earthImage, new Vector2(((double) frameNumber / getNumberOfFrames()), 0))
		));

		setCamera(new Camera(
				Vector3.ZERO,
				Vector3.ZERO,
				1
		));

		setAmbientLight(new Color(5, 5, 5));
		setDirectionalLight(new DirectionalLightSource(
				Color.WHITE,
                Vector3.EAST.add(Vector3.NORTH)
		));

		setSkyMaterial(new SolidColor(Color.BLACK));
	}
}
