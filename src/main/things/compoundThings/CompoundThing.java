package main.things.compoundThings;

import main.materials.MaterialData;
import main.math.vectors.Vector3;
import main.things.Thing;

public abstract class CompoundThing extends Thing {

	public CompoundThing() {
		super();
	}

	public CompoundThing(Vector3 position, Vector3 rotation) {
		super(position, rotation);
	}

	private Thing getClosestThing(Vector3 position) {
		Thing closestThingSoFar = null;
		double closestThingSoFarDistance = 0;
		for (Thing thing : this) {

			if (closestThingSoFar == null) {
				closestThingSoFar = thing;
				closestThingSoFarDistance = closestThingSoFar.getDistanceFrom(position);
				continue;
			}

			double thingDistance = thing.getDistanceFrom(position);

			if (closestThingSoFarDistance > thingDistance) {
				closestThingSoFar = thing;
				closestThingSoFarDistance = thingDistance;
			}
		}

		return closestThingSoFar;
	}

	@Override
	public double sdf(Vector3 position) {
		return getClosestThing(position).getDistanceFrom(position);
	}

	@Override
	public MaterialData getMaterialData(Vector3 position, Vector3 direction) {
		return getClosestThing(position).getMaterialData(position.subtract(getPosition()), direction);
	}
}
