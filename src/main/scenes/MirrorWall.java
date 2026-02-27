package main.scenes;

import main.materials.CheckerBoard;
import main.materials.Mirror;
import main.materials.SolidColor;
import main.math.vectors.Vector3;
import main.optics.Camera;
import main.optics.PointLightSource;
import main.things.Plane;
import main.things.Sphere;
import main.things.compoundThings.Scene;

import java.awt.*;

public class MirrorWall extends Scene {
	public MirrorWall() {
		super();

		add(new Plane(
				new Vector3(0, -1, 0),
				Vector3.UP,
				new CheckerBoard(
						new SolidColor(new Color(0, 255, 0)),
						new SolidColor(new Color(13, 106, 27))
				)
		));

		add(new Plane(
				new Vector3(8, 0, 0),
				Vector3.WEST,
				new CheckerBoard(
						new SolidColor(new Color(255, 0, 0)),
						new SolidColor(new Color(166, 6, 6))
				)
		));

		add(new Plane(
				new Vector3(-3, 0, 0),
				Vector3.EAST,
				new Mirror()
		));

		add(new Plane(
				new Vector3(0, 6, 0),
				Vector3.DOWN,
				new CheckerBoard(
						new SolidColor(new Color(234, 234, 234)),
						new SolidColor(new Color(128, 128, 128))
				)
		));

		add(new Plane(
				new Vector3(0, 0, 16),
				Vector3.SOUTH,
				new CheckerBoard(
						new SolidColor(new Color(0, 255, 149)),
						new SolidColor(new Color(6, 166, 105))
				)
		));


		add(new Sphere(
				new Vector3(0.5, 0, 8),
				1,
				new Mirror()
		));

		addPointLightSource(
				new PointLightSource(
						new Vector3(-.5, 3, 4),
						new Color(255, 255, 255),
						20
				)
		);

		setAmbientLight(new Color(60, 60, 60));

		setCamera(new Camera(
				new Vector3(6, 0, 0),
				new Vector3(0, Math.PI * -.25, 0),
				1
		));
	}
}
