package main.optics;

import main.math.vectors.Vector3;
import main.things.compoundThings.Scene;

import java.awt.*;

public class PointLightSource extends Vector3 {
	private Color color;
	private double intensity;

	public PointLightSource(Vector3 position, Color color, double intensity) {
		super(position);
		this.color = color;
		this.intensity = intensity;
	}

	public Color getColor() {
		return color;
	}

	public Vector3 apply(Vector3 totalLight, Vector3 positionOfPointToLight, Scene scene) {
		// Cast a new ray toward point light source. If ray ends up past it, we know it hits it too
		Vector3 directionTowardPointLight = this.subtract(positionOfPointToLight).normalize();
		Ray pointLightFinder = new Ray(positionOfPointToLight, directionTowardPointLight, scene);

		pointLightFinder.cast();

		double distance = distance(positionOfPointToLight);
		double intensityAfterInverseSquare = intensity / (distance * distance);

		boolean wentPastPointLight = pointLightFinder.getPosition().subtract(this).dotProduct(directionTowardPointLight) > 1;
		if (wentPastPointLight) {
			totalLight = LightUtils.applyLight(totalLight, new Vector3(this.getColor()).scale(intensityAfterInverseSquare), scene.getNormalAt(positionOfPointToLight), pointLightFinder.getDirection().negate());
		}

		return totalLight;
	}
}
