package main.materials;

import java.awt.*;

public class Mirror extends SolidMaterial {

	public Mirror() {
		super(null);
		super.materialData = new MaterialData(
				new Color(255, 255, 255),
				1
		);
	}
}
