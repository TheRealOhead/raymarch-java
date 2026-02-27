package main.materials;

import main.math.vectors.Vector3;

import java.awt.*;

public class Paper implements Material {

    private static final MaterialData materialData = new MaterialData(
        new Color(214, 214, 214), Vector3.ZERO, .7, .5, 1, 0
    );

    @Override
    public MaterialData getMaterialData(Vector3 position, Vector3 rayDirection) {
        return materialData;
    }
}
