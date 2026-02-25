package main.things;

import main.math.vectors.Vector3;

public interface HasSDF {
	double sdf(Vector3 position);
}
