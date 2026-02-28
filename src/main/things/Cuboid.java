package main.things;

import main.materials.Material;
import main.math.vectors.Vector3;
import main.rendering.Camera;

public class Cuboid extends Thing {

    public Cuboid(Vector3 position, Vector3 dimensions) {
        super(position);


    }

    @Override
    public Material getMaterial() {
        return null;
    }

    @Override
    public double sdf(Vector3 position) {

        return 0;
    }
}
