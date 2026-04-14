package main.things.compoundThings;

import main.materials.Material;
import main.materials.MaterialData;
import main.math.vectors.Vector3;
import main.things.Cuboid;
import main.things.Thing;

public class CompoundThing extends Thing {

	private CompoundThing subtraction = null;
	private CompoundThing conjunction = null;

	public CompoundThing() {
		super();
	}

	public CompoundThing(Vector3 position, Vector3 rotation) {
		super(position, rotation);
	}

	public void addSubtrahend(Thing thing) {
		if (subtraction == null)
			subtraction = new CompoundThing();

		subtraction.add(thing);
	}

	public void addConjunction(Thing thing) {
		if (conjunction == null)
			conjunction = new CompoundThing();

		conjunction.add(thing);
	}

    @Override
    public Material getMaterial() {
        return (position, rayDirection) -> getClosestThing(position).getMaterialData(position, rayDirection);
    }

	Thing getClosestThing(Vector3 position) {
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
		double dist = getClosestThing(position).getDistanceFrom(position);

		if (subtraction != null && !subtraction.isEmpty())
			dist = Math.max(
					dist,
					-subtraction.getClosestThing(position).getDistanceFrom(position)
			);

		if (conjunction != null && !conjunction.isEmpty())
			dist = Math.max(
					dist,
					conjunction.getClosestThing(position).getDistanceFrom(position)
			);

		return dist;
	}

	@Override
	public MaterialData getMaterialData(Vector3 position, Vector3 direction) {
		return getClosestThing(position).getMaterialData(position.subtract(getPosition()), direction);
	}
}
