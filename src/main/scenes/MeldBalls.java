package main.scenes;

import main.materials.CheckerBoard;
import main.materials.MaterialData;
import main.materials.SolidColor;
import main.materials.SolidMaterial;
import main.math.vectors.Vector3;
import main.rendering.Camera;
import main.optics.DirectionalLightSource;
import main.things.Plane;
import main.things.compoundThings.SmoothMinGroup;
import main.things.Sphere;
import main.things.compoundThings.Scene;

import java.awt.*;

public class MeldBalls extends Scene {
    @Override
    public int getNumberOfFrames() {
        return 32;
    }

    public MeldBalls() {
        this(0);
    }

	public MeldBalls(int currentFrame) {
		super(currentFrame);

        double progress = (double) currentFrame / getNumberOfFrames();


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
				new Vector3(Math.cos(progress * Math.PI * 2), 0, 8),
				1,
				new SolidMaterial(reflectiveRed)
		));

		balls.add(new Sphere(
				new Vector3(-Math.cos(progress * Math.PI * 2), 0, 8),
				1,
				new SolidMaterial(reflectiveGreen)
		));

		add(balls);

		setCamera(new Camera(
				new Vector3(0, 3, 0),
				new Vector3(0, 0, Math.PI * -.1),
				1
		));

		addDirectionalLight(new DirectionalLightSource(Color.WHITE, new Vector3(-1, -1, 1)));
	}
}
