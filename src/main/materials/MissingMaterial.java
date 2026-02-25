package main.materials;

import main.math.vectors.Vector3;

import java.awt.*;

public class MissingMaterial extends CheckerBoard {
	public MissingMaterial() {
		super(
				new SolidColor(Color.MAGENTA),
				new SolidColor(Color.BLACK),
				Vector3.ONE
		);
	}
}
