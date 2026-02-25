package main.materials;

import main.math.vectors.Vector3;

import java.awt.*;

public class MaterialData {
	public Color albedo = null;
	public Vector3 normalModifier = Vector3.ZERO;
	public double specularity = 0;
	public double scattering = 1;
	public double opacity = 1;
	public double refractiveIndex = 0;


	public MaterialData copy() {
		MaterialData clone = new MaterialData();

		clone.albedo = albedo;
		clone.normalModifier = normalModifier;
		clone.specularity = specularity;
		clone.scattering = scattering;
		clone.opacity = opacity;
		clone.refractiveIndex = refractiveIndex;

		return clone;
	}
}
