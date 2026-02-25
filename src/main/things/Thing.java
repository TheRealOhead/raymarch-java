package main.things;

import main.materials.Material;
import main.materials.MaterialData;
import main.math.vectors.Vector3;

import java.util.HashSet;

/**
 * A physical object represented by a signed distance function, position, and material
 */
public abstract class Thing extends HashSet<Thing> implements HasSDF {
	private final Vector3 position;

	public Thing() {
		this.position = Vector3.ZERO;
	}

	public abstract Material getMaterial();

	public Thing(Vector3 position) {
		this.position = position;
	}

	/**
	 * @return Position of Thing
	 */
	public Vector3 getPosition() {
		return position;
	}

	/**
	 * @param position Position to query Material
	 * @param direction Direction to query Material
	 * @return The MaterialData at the given location and direction
	 */
	public MaterialData getMaterialData(Vector3 position, Vector3 direction) {
		return getMaterial().getMaterialData(position.subtract(getPosition()), direction);
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	@Override
	public String toString() {
		return String.format("Thing at %s", getPosition());
	}
}
