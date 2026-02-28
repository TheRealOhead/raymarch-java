package main.scenes;

import main.materials.CheckerBoard;
import main.materials.SolidColor;
import main.materials.Water;
import main.math.vectors.Vector3;
import main.optics.PointLightSource;
import main.rendering.Camera;
import main.things.Plane;
import main.things.Sphere;
import main.things.compoundThings.CompoundThing;
import main.things.compoundThings.Scene;
import main.things.compoundThings.SmoothMinGroup;

import java.awt.*;

public class WaterBall extends Scene {

	public WaterBall() {
		this(0);
	}

	public WaterBall(int currentFrame) {
		super(currentFrame);

		WaterBall.numberOfFrames = 64;



		add(new Plane(
				new Vector3(3, 0, 0),
                Vector3.ZERO,
				Vector3.WEST,
				new CheckerBoard(
						new SolidColor(new Color(255, 0, 0)),
						new SolidColor(new Color(166, 6, 6))
				)
		));

		add(new Plane(
				new Vector3(-3, 0, 0),
                Vector3.ZERO,
				Vector3.EAST,
				new CheckerBoard(
						new SolidColor(new Color(132, 0, 255)),
						new SolidColor(new Color(102, 6, 166))
				)
		));

		add(new Plane(
				new Vector3(0, 6, 0),
                Vector3.ZERO,
				Vector3.DOWN,
				new CheckerBoard(
						new SolidColor(new Color(234, 234, 234)),
						new SolidColor(new Color(128, 128, 128))
				)
		));

		add(new Plane(
				new Vector3(0, 0, 16),
                Vector3.ZERO,
				Vector3.SOUTH,
				new CheckerBoard(
						new SolidColor(new Color(0, 255, 149)),
						new SolidColor(new Color(6, 166, 105))
				)
		));

		CompoundThing water = new SmoothMinGroup(1);
		add(water);

		water.add(new Plane(
				new Vector3(0, -1, 0),
                Vector3.ZERO,
				Vector3.UP,
				new Water(currentFrame)
		));

		water.add(new Sphere(
				new Vector3(0.5, Math.sin(((double) currentFrame / numberOfFrames) * Math.PI * 2) * 2, 8),
                Vector3.ZERO,
						1,
				new Water(currentFrame)
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
				new Vector3(0, 0, 0),
				new Vector3(0, 0, 0),
				1
		));
	}
}
