package main.scenes;

import main.materials.CheckerBoard;
import main.materials.SolidColor;
import main.math.vectors.Vector3;
import main.optics.DirectionalLightSource;
import main.rendering.Camera;
import main.things.Cuboid;
import main.things.Plane;
import main.things.Sphere;
import main.things.compoundThings.CompoundThing;
import main.things.compoundThings.Scene;

import java.awt.*;

public class ConjunctionDemo extends Scene {

	public ConjunctionDemo(int frame) {
		super(frame);
	}

	@Override
	public int getNumberOfFrames() {
		return 1;
	}

	@Override
	public void build(int frameNumber) {
		add(new Plane(
				new Vector3(0, -1, 0),
				Vector3.ZERO,
				Vector3.UP,
				new CheckerBoard(
						new SolidColor(new Color(140, 255, 0)),
						new SolidColor(new Color(58, 106, 13))
				)
		));

		CompoundThing group = new CompoundThing(
				new Vector3(0, 0, 12),
				Vector3.ZERO
		);
		add(group);

		group.add(new Sphere(
				Vector3.ZERO,
				Vector3.ZERO,
				false,
				1,
				new SolidColor(Color.BLUE)
		));

		group.addConjunction(new Cuboid(
				Vector3.ZERO,
				Vector3.ZERO,
				false,
				new Vector3(.8),
				new SolidColor(Color.GREEN)
		));

		add(new Plane(
				new Vector3(0, 0, 20),
				Vector3.ZERO,
				Vector3.SOUTH,
				new CheckerBoard(
						new SolidColor(new Color(40, 243, 128)),
						new SolidColor(new Color(29, 120, 69))

				)
		));

		setCamera(new Camera(
				new Vector3(0, 3, 0),
				new Vector3(0, 0, Math.PI * -.1),
				1
		));

		addDirectionalLight(new DirectionalLightSource(
				Color.WHITE,
				new Vector3(1, -1, 1)
		));
	}
}
