package main.materials;

import java.awt.*;

public class Water extends Rough {
	private static final MaterialData waterMaterialData = new MaterialData();

	public Water() {
		this( .25, .1);
	}

	public Water(double scale, double intensity) {
		super(new SolidMaterial(waterMaterialData), scale, intensity);

		waterMaterialData.albedo = new Color(161, 243, 255);
		waterMaterialData.specularity = .9;
	}


}
