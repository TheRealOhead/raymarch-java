package main.math.vectors;

public class Vector2 extends Vector3 {

	public Vector2(double x, double y) {
		super(x, y, 0);
	}

	public Vector2(double n) {
		this(n, n);
	}

	public Vector3 toVector3() {
		return new Vector3(x, y, 0);
	}

	public Vector3 toVector3(double z) {
		return new Vector3(x, y, z);
	}

	@Override
	public String toString() {
		return String.format("<%.3f, %.3f>", x, y);
	}
}
