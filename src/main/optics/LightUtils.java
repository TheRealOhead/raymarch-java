package main.optics;

import main.math.vectors.Vector3;

public class LightUtils {
	public static Vector3 applyLight(Vector3 totalLight, Vector3 lightToBeApplied, Vector3 normal, Vector3 lightAngle) {
		double dotProduct = Math.clamp(-normal.dotProduct(lightAngle), 0, 1);
		Vector3 obliqueLighting = lightToBeApplied.scale(dotProduct);
		return obliqueLighting.add(totalLight);
	}
}
