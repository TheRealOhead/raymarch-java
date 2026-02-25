package scenes;

import main.materials.*;
import main.math.vectors.Vector3;
import main.optics.Camera;
import main.things.Plane;
import main.things.Triangle;
import main.things.compoundThings.Scene;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SingleTriangle extends Scene {
	public SingleTriangle() throws IOException {
		add(new Triangle(
				new Vector3(-1, 0, 3.9),
				new Vector3(1, 0, 4.1),
				new Vector3(0, Math.sqrt(3), 4),
				new SolidColor(new Color(220, 201, 84))
		));

		add(new Plane(
				new Vector3(0, -12, 0),
				Vector3.UP,
				new Water(10, .125)
		));

		setCamera(new Camera(
				Vector3.ZERO,
				Vector3.ZERO,
				1
		));

		setSkyMaterial(new RadialTexture(ImageIO.read(new File("./images/sm64bg.jpg"))));
	}
}
