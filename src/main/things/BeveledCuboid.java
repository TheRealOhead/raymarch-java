package main.things;

import main.materials.Material;
import main.math.vectors.Vector3;

public class BeveledCuboid extends Cuboid {
	private final double radius;
	public BeveledCuboid(Vector3 position, Vector3 rotation, boolean inverted, double radius, Vector3 dimensions, Material material) {
		super(position, rotation, inverted, dimensions, material);
		this.radius = radius;
	}

	@Override
	public double sdf(Vector3 position) {
		return super.sdf(position) - radius;
	}
}
