package main.materials;

import main.math.PerlinNoise;
import main.math.vectors.Vector3;

public class Rough implements Material {

	private final Material baseMaterial;
	private final double scale;
	private final double intensity;

	public Rough(Material baseMaterial, double scale, double intensity) {
		this.baseMaterial = baseMaterial;
		this.scale = scale;
		this.intensity = intensity;
	}

	@Override
	public MaterialData getMaterialData(Vector3 position, Vector3 rayDirection) {
		MaterialData oldData = baseMaterial.getMaterialData(position, rayDirection);
		position = position.scale(1 / scale);

		return new MaterialData(
				oldData.albedo(),
				oldData.normalModifier().add(PerlinNoise.noise3D(position.x, position.y, position.z).scale(intensity)),
				oldData.specularity(),
				oldData.scattering(),
				oldData.opacity(),
				oldData.refractiveIndex()
		);
	}
}
