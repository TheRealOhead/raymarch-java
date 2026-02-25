package main.materials;

import java.awt.*;

public class Mirror extends SolidMaterial {

	public Mirror() {
		super(null);
		super.materialData = new MaterialData();
		super.materialData.albedo = new Color(255, 255, 255);
		super.materialData.specularity = 1;
	}
}
