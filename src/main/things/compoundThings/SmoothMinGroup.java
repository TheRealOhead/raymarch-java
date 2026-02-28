package main.things.compoundThings;

import main.materials.Material;
import main.materials.MaterialData;
import main.math.Mean;
import main.math.vectors.Vector3;
import main.things.Thing;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;

public class SmoothMinGroup extends CompoundThing {
	private final double deviation;

	public SmoothMinGroup(double deviation) {
		this(Vector3.ZERO, Vector3.ZERO, deviation);
	}

	public SmoothMinGroup(Vector3 position, Vector3 rotation, double deviation) {
		super(position, rotation);
		this.deviation = deviation;
	}

	private double smoothMin(Collection<Double> numbers) {
		/*
		 * This is adapted from the following GLSL exponential smooth min function at https://iquilezles.org/articles/smin/
		 *
		 * float smin( float a, float b, float k )
		 * {
		 * 	k *= 1.0;
		 * 	float r = exp2(-a/k) + exp2(-b/k);
		 * 	return -k*log2(r);
		 * }
		 */

		double sum = 0;
		for (double number : numbers) {
			sum += Math.pow(10, -number / this.deviation);
		}
		return -this.deviation * Math.log10(sum);
	}

	@Override
	public double sdf(Vector3 position) {
		LinkedList<Double> numbers = new LinkedList<>();
		for (Thing thing : this) {
			numbers.add(thing.getDistanceFrom(position));
		}
		return smoothMin(numbers);
	}

	@Override
	public Material getMaterial() {
		return null;
	}

	@Override
	public MaterialData getMaterialData(Vector3 rayPosition, Vector3 rayDirection) {
		MaterialData oldMaterial = SmoothMinGroup.super.getMaterialData(rayPosition, rayDirection);

		LinkedList<Vector3> albedo = new LinkedList<>();

		LinkedList<Double> specularity = new LinkedList<>();

		LinkedList<Vector3> normal = new LinkedList<>();

		LinkedList<Double> weights = new LinkedList<>();

		for (Thing thing : SmoothMinGroup.this) {
			MaterialData data = thing.getMaterialData(rayPosition, rayDirection);

			albedo.add(new Vector3(data.albedo()));

			specularity.add(data.specularity());

			normal.add(data.normalModifier());

			weights.add(1 / thing.sdf(rayPosition));
		}

		Color newAlbedo = Mean.weightedRootMeanSquareOfVector3(albedo, weights).asColor();
		double newSpecularity = Mean.weightedRootMeanSquare(specularity, weights);
		Vector3 newNormalModifier = Mean.weightedRootMeanSquareOfVector3(normal, weights);

		return new MaterialData(
				newAlbedo,
				newNormalModifier,
				newSpecularity,
				oldMaterial.scattering(),
				oldMaterial.opacity(),
				oldMaterial.refractiveIndex()
		);
	}

}
