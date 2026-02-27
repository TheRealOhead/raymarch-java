package main.scenes;

import main.materials.CheckerBoard;
import main.materials.MaterialData;
import main.materials.SolidColor;
import main.materials.SolidMaterial;
import main.math.vectors.Vector3;
import main.optics.Camera;
import main.optics.DirectionalLightSource;
import main.things.Plane;
import main.things.compoundThings.SmoothMinGroup;
import main.things.Sphere;
import main.things.compoundThings.Scene;

import java.awt.*;

public class MeldBalls extends Scene {
	public MeldBalls() {
		super();



		add(new Plane(
				new Vector3(0, -1, 0),
				Vector3.UP,
				new CheckerBoard(
						new SolidColor(new Color(101, 186, 23)),
						new SolidColor(new Color(58, 106, 13))
				)
		));

		MaterialData reflectiveRed = new MaterialData(
				new Color(255, 0, 0),
				.9
		);

		MaterialData reflectiveGreen = new MaterialData(
				new Color(0, 255, 0),
				.9
		);

		SmoothMinGroup balls = new SmoothMinGroup(Vector3.ZERO, 0.6);
		//World balls = new World();

		balls.add(new Sphere(
				new Vector3(-1, 0, 8),
				1,
				new SolidMaterial(reflectiveRed)
		));

		balls.add(new Sphere(
				new Vector3(1, 0, 8),
				1,
				new SolidMaterial(reflectiveGreen)
		));

		add(balls);

		setCamera(new Camera(
				new Vector3(0, 3, 0),
				new Vector3(0, 0, Math.PI * -.1),
				1
		));

		setDirectionalLight(new DirectionalLightSource(Color.WHITE, new Vector3(-1, -1, 1)));
	}
}
