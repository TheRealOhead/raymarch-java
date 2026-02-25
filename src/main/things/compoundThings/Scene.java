package main.things.compoundThings;

import main.math.vectors.Vector3;
import main.optics.Camera;
import main.optics.DirectionalLightSource;
import main.optics.PointLightSource;
import main.optics.Ray;
import main.materials.Sky;
import main.materials.Material;
import main.things.Thing;

import java.awt.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A collection of {@link Thing}s
 */
public class Scene extends CompoundThing {

	public int currentFrame = 0;
	public static int numberOfFrames = 1;

	public Scene(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getNumberOfFrames() {
		return numberOfFrames;
	}

	DirectionalLightSource directionalLightSource = null;

	private Camera camera = null;

	Material skyMaterial = Sky.DEFAULT;

	Set<PointLightSource> pointLightSources = new HashSet<>();

	public Scene() {
		super();
	}

	@Override
	public Material getMaterial() {
		return null;
	}

	public Scene(Vector3 position) {
		super(position);
	}

	public void setDirectionalLight(DirectionalLightSource directionalLightSource) {
		this.directionalLightSource = directionalLightSource;
	}

	public void setSkyMaterial(Material material) {
		this.skyMaterial = material;
	}
	public Material getSkyMaterial() {
		return this.skyMaterial;
	}

	public DirectionalLightSource getDirectionalLight() {
		return directionalLightSource;
	}

	private Color ambientLight = new Color(124, 124, 124);
	public Color getAmbientLight() {
		return ambientLight;
	}

	public void setAmbientLight(Color color) {
		this.ambientLight = color;
	}

	public void addPointLightSource(PointLightSource pointLightSource) {
		pointLightSources.add(pointLightSource);
	}

	public Collection<PointLightSource> getPointLights() {
		return this.pointLightSources;
	}

	public Vector3 getNormalAt(Vector3 position) {
		double x = sdf(position.add(Vector3.EAST .scale(Ray.NORMAL_EPSILON)));
		double y = sdf(position.add(Vector3.UP   .scale(Ray.NORMAL_EPSILON)));
		double z = sdf(position.add(Vector3.NORTH.scale(Ray.NORMAL_EPSILON)));
		return new Vector3(x, y, z).normalize();
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
		camera.setWorld(this);
	}

	public Camera getCamera() {
		return camera;
	}
}
