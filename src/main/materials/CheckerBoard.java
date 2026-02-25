package main.materials;

import main.math.vectors.Vector3;

public class CheckerBoard implements Material {
	private Material colorA;
	private Material colorB;
	private Vector3 scale;

	public CheckerBoard(Material colorA, Material colorB, Vector3 scale) {
		this.colorA = colorA;
		this.colorB = colorB;
		this.scale = scale;
	}

	public CheckerBoard(Material colorA, Material colorB) {
		this.colorA = colorA;
		this.colorB = colorB;
		this.scale = Vector3.ONE;
	}

	@Override
	public MaterialData getMaterialData(Vector3 position, Vector3 rayDirection) {
		position = position.multiply(this.scale);
		boolean whichColor = (Math.floor(position.x) + Math.floor(position.y) + Math.floor(position.z)) % 2 == 0;
		return whichColor ?
				colorA.getMaterialData(position, rayDirection):
				colorB.getMaterialData(position, rayDirection);
	}
}
