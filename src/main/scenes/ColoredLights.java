package main.scenes;

import main.materials.SolidColor;
import main.math.vectors.Vector3;
import main.optics.DirectionalLightSource;
import main.rendering.Camera;
import main.things.Plane;
import main.things.Sphere;
import main.things.compoundThings.Scene;

import java.awt.*;

public class ColoredLights extends Scene {
	public ColoredLights(int frameNumber) {
        super(frameNumber);

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

        double triangleScale = .1;

        addDirectionalLight(new DirectionalLightSource(Color.RED,
                new Vector3(-1, 0, -2/Math.sqrt(3)).scale(triangleScale).add(Vector3.DOWN)
        ));
        addDirectionalLight(new DirectionalLightSource(Color.GREEN,
                new Vector3(1, 0, -2/Math.sqrt(3)).scale(triangleScale).add(Vector3.DOWN)
        ));
        addDirectionalLight(new DirectionalLightSource(Color.BLUE,
                new Vector3(0, 0, 2/Math.sqrt(3)).scale(triangleScale).add(Vector3.DOWN)
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
