package main.optics;

import main.materials.MaterialData;
import main.math.vectors.Vector3;
import main.things.compoundThings.Scene;

import java.awt.*;

public class Ray {
	private Vector3 position;
	private Vector3 direction;
	private Scene scene;

	private double depth;
	public double getDepth() { return depth; }

	private double stepsTaken = 0;
	public double getStepsTaken() { return stepsTaken; }

	public MaterialData materialData;

	public final static int MAX_STEPS = 1000;
	private final static double MAX_DIST = 1000;
	private final static double MIN_DIST = .0001;
	public final static double ESCAPE_EPSILON = .001;
	public final static double NORMAL_EPSILON = .01;

	public static final int RECURSION_COUNT = 2;
	private final static int RAYS_PER_REFLECTION = 1;


	public Ray(Vector3 position, Vector3 direction, Scene scene) {
		this.position = position;
		this.direction = direction;
		this.scene = scene;
	}

	/**
	 * Goes into the world and retrieves a color value, calculating lighting along the way
	 */
	public Color march(int recursionCount) {

		Vector3 startingPosition = this.position;
		boolean hitForAlbedo = cast();
		depth = startingPosition.distance(this.position);

		if (!hitForAlbedo) {
			materialData = scene.getSkyMaterial().getMaterialData(position, direction);
			return materialData.albedo();
		}

		materialData = scene.getMaterialData(this.position, this.direction);

		Vector3 specularLight = Vector3.ZERO;
		specularLight = applySpecularLight(specularLight, materialData, recursionCount);

		Vector3 diffuseLight = Vector3.ZERO;
		diffuseLight = applyAmbientLight(diffuseLight, materialData);
		diffuseLight = applyPointLights(diffuseLight, materialData);
		diffuseLight = applyDirectionalLight(diffuseLight, materialData);

		Vector3 totalLight = Vector3.lerp(specularLight, diffuseLight, materialData.specularity());

		return (new Vector3(materialData.albedo())).multiply(totalLight).asColor();
	}

	private Vector3 applySpecularLight(Vector3 totalLight, MaterialData materialData, int recursionCount) {
		Vector3 specularLight = new Vector3(0, 0, 0);
		if (materialData.specularity() > 0 && recursionCount > 0) {
			for (int i = 0; i < RAYS_PER_REFLECTION; i++) {
				Ray child = new Ray(position, calculateReflection(materialData), scene);
				specularLight = specularLight.add(new Vector3(child.march(recursionCount - 1)));
				stepsTaken += child.stepsTaken;
			}
			specularLight = specularLight.scale(1 / ((double) RAYS_PER_REFLECTION));
			specularLight = specularLight.scale(materialData.specularity());
		}
		return totalLight.add(specularLight);
	}

	/**
	 * Takes the world normal into account to see what direction the ray would travel in if it were to reflect off of the world surface
	 * @param materialData the materialData of the object being reflected off of
	 * @return Facing normal post-reflection
	 */
	private Vector3 calculateReflection(MaterialData materialData) {
		Vector3 normal = getWorldNormal(materialData);
		// Equation from https://www.sunshine2k.de/articles/coding/vectorreflection/vectorreflection.html
		// R = D - N * (N dotprod D)
		return direction.subtract(normal.scale(2 * direction.dotProduct(normal))).normalize();
	}

	private Vector3 applyDirectionalLight(Vector3 totalLight, MaterialData materialData) {
		DirectionalLightSource directionalLightSource = scene.getDirectionalLight();
		if (directionalLightSource == null) return totalLight;

		Ray secondaryProbe = new Ray(position, directionalLightSource.getNormal().negate(), scene);
		if (!secondaryProbe.cast())
			totalLight = LightUtils.applyLight(
					totalLight,
					new Vector3(directionalLightSource.getColor()),
					getWorldNormal(materialData),
					directionalLightSource.getNormal()
			);
		return totalLight;
	}

	private Vector3 applyPointLights(Vector3 totalLight, MaterialData materialData) {
		for (PointLightSource pointLightSource : scene.getPointLights())
			totalLight = pointLightSource.apply(totalLight, position, scene);
		return totalLight;
	}

	Vector3 getWorldNormal(MaterialData materialData) {
		return scene.getNormalAt(position).add(materialData.normalModifier()).normalize();
	}

	private Vector3 applyAmbientLight(Vector3 totalLight, MaterialData materialData) {
		return new Vector3(scene.getAmbientLight()).add(totalLight);
	}

	/**
	 * Sends a ray off in the direction it's facing
	 * @return Whether it hit anything
	 */
	boolean cast() {
		stepFixedDistance(ESCAPE_EPSILON);
		int step;
		for (step = 0; step < MAX_STEPS; step++) {

			if (stepAsFarAsPossible())
				break;
		}

		return scene.sdf(position) < MIN_DIST;
	}

	/**
	 * Step a fixed distance regardless of whether it hits anything
	 * @param distance Distance to step
	 */
	public void stepFixedDistance(double distance) {
		this.position = this.position.add(this.direction.scale(distance));

		stepsTaken++;
	}

	/**
	 * Steps as far as it safely can without hitting anything
	 * @return Whether the ray got too close to or too far from anything. I.e., stop stepping
	 */
	public boolean stepAsFarAsPossible() {
		double distanceValueBeforeMove = scene.sdf(position);

		boolean tooClose = distanceValueBeforeMove < MIN_DIST;
		boolean tooFar = distanceValueBeforeMove > MAX_DIST;
		boolean goNoFurther = tooFar || tooClose;

		if (goNoFurther) {
			return true;
		}

		stepFixedDistance(distanceValueBeforeMove);

		double distanceValueAfterMove = scene.sdf(position);




		return false;
	}

	public Vector3 getPosition() {
		return position;
	}

	public Vector3 getDirection() {
		return direction;
	}
}
