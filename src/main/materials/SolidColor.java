package main.materials;

import main.math.vectors.Vector3;

import java.awt.*;

public class SolidColor implements Material {
	MaterialData materialData = new MaterialData();

	public SolidColor(Color color) {
		this.materialData.albedo = color;
	}

	@Override
	public MaterialData getMaterialData(Vector3 position, Vector3 facing) {
		return this.materialData;
	}
}
