package main.materials;

import main.math.vectors.Vector3;

import java.awt.*;

public class SandedSteel extends SolidMaterial {
    public SandedSteel() {
        super(new MaterialData(Color.WHITE, Vector3.ZERO, .9, .5, 1, 0));
    }
}
