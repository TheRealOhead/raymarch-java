package main.materials;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.core.api.noisegen.NoiseResult;
import de.articdive.jnoise.generators.noisegen.perlin.PerlinNoiseGenerator;
import de.articdive.jnoise.pipeline.JNoise;
import de.articdive.jnoise.pipeline.JNoiseDetailed;
import main.math.vectors.Vector3;

public class Rough implements Material {

	private final Material baseMaterial;
	private final double scale;
	private final double intensity;
	private final double offset;

	public Rough(Material baseMaterial, double scale, double intensity, double offset) {
		this.baseMaterial = baseMaterial;
		this.scale = scale;
		this.intensity = intensity;
		this.offset = offset;
	}

	@Override
	public MaterialData getMaterialData(Vector3 position, Vector3 rayDirection) {
		MaterialData oldData = baseMaterial.getMaterialData(position, rayDirection);
		position = position.scale(1 / scale);

		PerlinNoiseGenerator noiseX = PerlinNoiseGenerator.newBuilder()
				.setSeed(0)
				.setInterpolation(Interpolation.LINEAR)
				.build();

		PerlinNoiseGenerator noiseY = PerlinNoiseGenerator.newBuilder()
				.setSeed(1)
				.setInterpolation(Interpolation.LINEAR)
				.build();

		PerlinNoiseGenerator noiseZ = PerlinNoiseGenerator.newBuilder()
				.setSeed(2)
				.setInterpolation(Interpolation.LINEAR)
				.build();

		return new MaterialData(
				oldData.albedo(),
				oldData.normalModifier().add(new Vector3(
						noiseX.evaluateNoise(
								position.x,
								position.y,
								position.z,
								offset
						),
						noiseY.evaluateNoise(
								position.x,
								position.y,
								position.z,
								offset
						),
						noiseZ.evaluateNoise(
								position.x,
								position.y,
								position.z,
								offset
						)
				).scale(intensity)),
				oldData.specularity(),
				oldData.scattering(),
				oldData.opacity(),
				oldData.refractiveIndex()
		);
	}
}
