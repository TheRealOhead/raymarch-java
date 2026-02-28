package main.optics;

import main.math.vectors.Vector3;

import java.awt.*;

public class DirectionalLightSource {
	public final static DirectionalLightSource DEFAULT = new DirectionalLightSource(
			new Color(255, 243, 188),
			new Vector3( 1, -1, -1).normalize()
	);

	private Color color;
	private Vector3 normal;

	public DirectionalLightSource(Color color, Vector3 normal) {
		this.color = color;
		this.normal = normal.normalize();
	}

	public Vector3 getNormal() {
		return normal;
	}

	public Color getColor() {
		return color;
	}
}
