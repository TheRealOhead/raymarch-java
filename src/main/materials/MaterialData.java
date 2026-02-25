package main.materials;

import main.math.vectors.Vector3;

import java.awt.*;

public record MaterialData(
		Color albedo,
		Vector3 normalModifier,
		double specularity,
		double scattering,
		double opacity,
		double refractiveIndex
) {

	public MaterialData(
			Color albedo
	) {
		this(
				albedo,
				Vector3.ZERO,
				0,
				1,
				1,
				0
		);
	}

	public MaterialData(
			Color albedo,
			double specularity) {
		this(
				albedo,
				Vector3.ZERO,
				specularity,
				1,
				1,
				0
		);
	}
}
