package main.materials;

import main.math.vectors.Vector3;

import java.awt.*;

/**
 * Meant for when the ray has traveled too far. Displays a gradient based on the ray's heading direction
 */
public class Sky implements Material {
	public final static Sky DEFAULT = new Sky(
				new Color(159, 214, 228),
				new Color(141, 174, 228),
				new Color(66, 66, 66)
		);

	private Color top;
	private Color middle;
	private Color bottom;

	public Sky(Color top, Color middle, Color bottom) {
		this.top = top;
		this.middle = middle;
		this.bottom = bottom;
	}

	@Override
	public MaterialData getMaterialData(Vector3 position, Vector3 direction) {
		Color albedo;
		if (direction.y > 0) {
			albedo = Vector3.lerp(new Vector3(top), new Vector3(middle), direction.y).asColor();
		} else {
			albedo = Vector3.lerp(new Vector3(middle), new Vector3(bottom), direction.y + 1).asColor();
		}
		return new MaterialData(albedo);
	}
}
