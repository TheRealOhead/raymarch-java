package main.scenes;

import main.materials.CheckerBoard;
import main.materials.Mirror;
import main.materials.SandedSteel;
import main.materials.SolidColor;
import main.math.vectors.Vector3;
import main.optics.PointLightSource;
import main.rendering.Camera;
import main.things.Plane;
import main.things.Sphere;
import main.things.compoundThings.Scene;

import java.awt.*;

public class MirrorFacingMirror extends Scene {

	public MirrorFacingMirror() {
		this(0);
	}

	public MirrorFacingMirror(int currentFrame) {
		super(currentFrame);

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
				new Mirror()
		));


		add(new Plane(
				new Vector3(0, -1, 0),
                Vector3.ZERO,
				Vector3.UP,
				new CheckerBoard(
                        new SolidColor(new Color(29, 190, 243)),
                        new SolidColor(new Color(23, 96, 120))
                )
		));

        add(new Plane(
                new Vector3(0, 0, -1),
                Vector3.ZERO,
                Vector3.NORTH,
                new Mirror()
        ));

		add(new Sphere(
				new Vector3(1.5, -.5, 8),
                Vector3.ZERO,
                .5,
				new SolidColor(Color.YELLOW)
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


/*
    Ball in the Box by Alice in Method Chains

    I'm the ball in the box, buried in my SDF
    Won't you come and save me, save me?
    Feed my rays, can you output a fragment?
    Sun Microsystems, deny your maker
    He who tries, will be rendered
    Feed my rays, now you've outputted a fragment
    I'm the Object who gets referenced, shove my memory address in the garbage collector
    Won't you come and render me, render me?
    Feed my rays, can you output a fragment?
    Sun Microsystems, deny your maker
    He who tries, will be rendered
    Feed my rays, now you've outputted a fragment
    Feed my rays, can you output a fragment?
    Sun Microsystems, deny your maker
    He who tries, will be rendered
    Feed my rays, now you've outputted a fragment
*/