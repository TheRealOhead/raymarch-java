package main.things;

import main.materials.Material;
import main.materials.MaterialData;
import main.math.vectors.Vector2;
import main.math.vectors.Vector3;
import main.rendering.Camera;

public class Cuboid extends Thing {

    private final Material material;
    private final Vector3 dimensions;

    public Cuboid(Vector3 position, Vector3 rotation, Vector3 dimensions, Material material) {
        super(position, rotation);
        this.material = material;
        this.dimensions = dimensions;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public double sdf(Vector3 position) {
        Vector3 absPosition = new Vector3(
                Math.abs(position.x),
                Math.abs(position.y),
                Math.abs(position.z)
        );

        boolean aboveXFace = absPosition.x > dimensions.x;
        boolean aboveYFace = absPosition.y > dimensions.y;
        boolean aboveZFace = absPosition.z > dimensions.z;

        if (aboveXFace && aboveYFace && aboveZFace)
            return absPosition.distance(dimensions);

        if (aboveXFace && aboveYFace)
            return new Vector2(absPosition.x, absPosition.y).distance(new Vector2(dimensions.x, dimensions.y));
        if (aboveZFace && aboveYFace)
            return new Vector2(absPosition.z, absPosition.y).distance(new Vector2(dimensions.z, dimensions.y));
        if (aboveXFace && aboveZFace)
            return new Vector2(absPosition.x, absPosition.z).distance(new Vector2(dimensions.x, dimensions.z));

        if (aboveXFace)
            return absPosition.x - dimensions.x;

        if (aboveYFace)
            return absPosition.y - dimensions.y;

        return absPosition.z - dimensions.z;
    }
}
