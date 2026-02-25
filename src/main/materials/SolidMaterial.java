package main.materials;

import main.math.vectors.Vector3;

public class SolidMaterial implements Material {

	MaterialData materialData;

	public SolidMaterial(MaterialData materialData) {
		this.materialData = materialData;
	}

	@Override
	public MaterialData getMaterialData(Vector3 position, Vector3 rayDirection) {
		return this.materialData;
	}
}
