package main.scenes;

import main.materials.SolidColor;
import main.math.vectors.Vector3;
import main.optics.Camera;
import main.optics.PointLightSource;
import main.things.Plane;
import main.things.Sphere;
import main.things.compoundThings.Scene;

import java.awt.*;

public class ColoredLights extends Scene {
	public ColoredLights() {
		add(new Sphere(
				new Vector3(0, 0, 4),
				1,
				new SolidColor(Color.GRAY)
		));

		add(new Plane(
				new Vector3(0, -4, 0),
				Vector3.UP,
				new SolidColor(Color.WHITE)
		));

		addPointLightSource(new PointLightSource(
			new Vector3(-1.25, 4, 5),
				Color.RED,
			5
		));

		addPointLightSource(new PointLightSource(
				new Vector3(0, 4, 3),
				Color.GREEN,
				5
		));

		addPointLightSource(new PointLightSource(
				new Vector3(1.25, 4, 5),
				Color.BLUE,
				5
		));

		setCamera(new Camera(
				new Vector3(0, 1, -8),
				new Vector3(0, 0, Math.PI * -.05),
				1
		));

		setSkyMaterial(new SolidColor(Color.BLACK));
		setAmbientLight(new Color(46, 46, 46));
	}
}
