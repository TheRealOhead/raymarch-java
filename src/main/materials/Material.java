package main.materials;

import main.math.vectors.Vector3;

public interface Material {
	MaterialData getMaterialData(Vector3 position, Vector3 rayDirection);
}
