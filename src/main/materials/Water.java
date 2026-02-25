package main.materials;

import java.awt.*;

public class Water extends Rough {
	private static final MaterialData defaultMaterialData = new MaterialData(
			new Color(161, 243, 255),
			.9
	);

	public Water() {
		this( .25, .1);
	}

	public Water(double scale, double intensity) {
		super(new SolidMaterial(defaultMaterialData), scale, intensity);
	}


}
