package main.materials;

import java.awt.*;

public class Water extends Rough {
	private static final MaterialData defaultMaterialData = new MaterialData(
			new Color(161, 243, 255),
			.9
	);

	public Water(int frame) {
		this( .25, .1, 1, frame);
	}

	public Water(double scale, double intensity, double speed, int frame) {
		super(new SolidMaterial(defaultMaterialData), scale, intensity, frame * speed / 100);
	}
}
