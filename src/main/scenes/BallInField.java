package main.scenes;

import main.materials.*;
import main.math.vectors.Vector3;
import main.rendering.Camera;
import main.optics.DirectionalLightSource;
import main.things.Plane;
import main.things.Sphere;
import main.things.compoundThings.Scene;

import java.awt.*;

public class BallInField extends Scene {
	public BallInField() {
		super();

		add(new Plane(
				new Vector3(0, -1, 0),
				Vector3.UP,
				new CheckerBoard(
						new SolidColor(new Color(140, 255, 0)),
						new SolidColor(new Color(58, 106, 13))
				)
		));

		add(new Sphere(
				new Vector3(0, 0, 8),
				1,
				new SolidColor(Color.RED)
		));

		setCamera(new Camera(
				new Vector3(0, 3, 0),
				new Vector3(0, 0, Math.PI * -.1),
				1
		));

		setDirectionalLight(DirectionalLightSource.DEFAULT);
	}
}
