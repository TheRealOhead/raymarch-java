package main.things;

import main.materials.Material;
import main.math.vectors.Vector3;

public class Sphere extends Thing {
	private final double radius;
	private final Material material;

	public Sphere(Vector3 position, Vector3 rotation, double radius, Material material) {
		super(position, rotation);
		this.radius = radius;
		this.material = material;
	}

	@Override
	public double sdf(Vector3 position) {
		return position.magnitude() - this.radius;
	}

	@Override
	public Material getMaterial() {
		return material;
	}

	@Override
	public String toString() {
		return String.format("Sphere with radius %.2f at %s", radius, getPosition());
	}
}