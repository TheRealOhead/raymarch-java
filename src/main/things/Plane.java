package main.things;

import main.materials.Material;
import main.math.vectors.Vector3;

public class Plane extends Thing {

	private Vector3 normal;
	private Material material;

	public Plane(Vector3 position, Vector3 normal, Material material) {
		super(position);
		this.normal = normal.normalize();
		this.material = material;
	}

	@Override
	public double sdf(Vector3 position) {
		return position.subtract(getPosition()).dotProduct(this.normal);
	}

	@Override
	public Material getMaterial() {
		return material;
	}
}
