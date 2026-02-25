package main.math.vectors;

/**
 * This is kind of useless, but it might help with generalizing concepts to lower dimensions
 */
public class Scalar extends Vector2 {

	public Scalar(double x) {
		super(x, 0);
	}

	public Vector2 toVector2() {
		return new Vector2(x, 0);
	}

	public Vector2 toVector2(double y) {
		return new Vector2(x, y);
	}

	public Vector3 toVector3(double y, double z) {
		return new Vector3(x, y, z);
	}

	@Override
	public String toString() {
		return String.format("<%.3f>", x);
	}
}
