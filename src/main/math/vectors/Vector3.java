package main.math.vectors;

import java.awt.*;
import java.util.Formattable;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An immutable 3D vector of doubles
 */
public class Vector3 {
	public final double x;
	public final double y;
	public final double z;

	public final static Vector3 ZERO = new Vector3(0);
	public final static Vector3 ONE = new Vector3(1);
	public final static Vector3 UP = new Vector3(0, 1, 0);
	public final static Vector3 DOWN = new Vector3(0, -1, 0);
	public final static Vector3 NORTH = new Vector3(0, 0, 1);
	public final static Vector3 SOUTH = new Vector3(0, 0, -1);
	public static final Vector3 EAST = new Vector3(1, 0, 0);
	public static final Vector3 WEST = new Vector3(-1, 0, 0);

	public static final Vector3 ROLL  = new Vector3(Math.PI * 2, 0, 0);
	public static final Vector3 YAW   = new Vector3(0, Math.PI * 2, 0);
	public static final Vector3 PITCH = new Vector3(0, 0, Math.PI * 2);

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3(double n) {
		this(n, n, n);
	}

	public Vector3(Vector3 original) {
		this(original.x, original.y, original.z);
	}

	public Vector3(Color color) {
		this(
				(double) color.getRed()   / 256,
				(double) color.getGreen() / 256,
				(double) color.getBlue()  / 256
		);
	}

    public static Vector3 getRandomUnitVectorInHemisphere(Vector3 hemisphere) {
        Vector3 randomVector;
        do {
            randomVector = getRandomUnitVector();
        } while (randomVector.dotProduct(hemisphere) > 0);
        return randomVector;
    }

    public static Vector3 getRandomUnitVector() {
        Vector3 randomVector;
        do {
            randomVector = new Vector3(
                    ThreadLocalRandom.current().nextDouble() * 2 - 1,
                    ThreadLocalRandom.current().nextDouble() * 2 - 1,
                    ThreadLocalRandom.current().nextDouble() * 2 - 1
            );
        } while (randomVector.magnitudeSquared() > 1);
        return randomVector.normalize();
    }

    public Vector3 subtract(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	public Vector3 multiply(Vector3 other) {
		return new Vector3(
				x * other.x,
				y * other.y,
				z * other.z
		);
	}

	public int xInt() {
		return (int) x;
	}
	public int yInt() {
		return (int) y;
	}
	public int zInt() {
		return (int) z;
	}

	public Vector3 squareRoot() {
		return new Vector3(
				Math.sqrt(x),
				Math.sqrt(y),
				Math.sqrt(z)
		);
	}

	public Vector3 crossProduct(Vector3 other) {
		return new Vector3(
				y*other.z - z*other.y,
				z*other.x - x*other.y,
				x*other.y - y*other.x
		);
	}

	public Vector3 add(Vector3 other) {
		return new Vector3(
				x + other.x,
				y + other.y,
				z + other.z
		);
	}

	public Vector3 modulo(double divisor) {
		return new Vector3(
				x % divisor,
				y % divisor,
				z % divisor
		);
	}

    public Vector3 squared() {
        return new Vector3(
                x*x,
                y*y,
                z*z
        );
    }

    private interface Operation {
		double operate(double a, double b);
	}

	public Vector3 scale(double scalar) {
		return new Vector3(x * scalar, y * scalar, z * scalar);
	}

	public double magnitude() {
		return Math.sqrt(x*x + y*y + z*z);
	}

	public double magnitudeSquared() {
		return x*x + y*y + z*z;
	}

	public Vector3 negate() {
		return scale(-1);
	}

	public Vector3 normalize() {
		return scale(1 / magnitude());
	}

	public double dotProduct(Vector3 other) {
		return x * other.x + y * other.y + z * other.z;
	}

	public double distance(Vector3 other) {
		return subtract(other).magnitude();
	}

	public Vector2 toVector2() {
		return new Vector2(x, y);
	}

	public static Vector3 lerp(Vector3 pointA, Vector3 pointB, double t) {
		return new Vector3(
				(pointA.x * (1 - t)) + (t * pointB.x),
				(pointA.y * (1 - t)) + (t * pointB.y),
				(pointA.z * (1 - t)) + (t * pointB.z)
		);
	}

	public Color asColor() {
		return new Color(
				(int) Math.clamp(x * 256, 0, 255),
				(int) Math.clamp(y * 256, 0, 255),
				(int) Math.clamp(z * 256, 0, 255));
	}

	public Vector3 rotate(Vector3 rotation) {
		return rotate(
				rotation.x,
				rotation.y,
				rotation.z
		);
	}

	public Vector3 rotate(double roll, double yaw, double pitch) {
		Vector3 rotated = this;

		rotated = new Vector3(
				Math.cos(yaw) * rotated.x + Math.sin(yaw) * rotated.z,
				rotated.y,
				Math.cos(yaw) * rotated.z - Math.sin(yaw) * rotated.x);


		rotated = new Vector3(
				rotated.x,
				Math.cos(pitch) * rotated.y + Math.sin(pitch) * rotated.z,
				Math.cos(pitch) * rotated.z - Math.sin(pitch) * rotated.y
				);

		rotated = new Vector3(
				Math.cos(roll) * rotated.x - Math.sin(roll) * rotated.y,
				Math.cos(roll) * rotated.y + Math.sin(roll) * rotated.x,
				rotated.z
		);

		return rotated;
	}

	public Vector3 reflect(Vector3 normal) {
		// Equation from https://www.sunshine2k.de/articles/coding/vectorreflection/vectorreflection.html
		// R = D - N * (N dot D)
		return subtract(normal.scale(2 * dotProduct(normal))).normalize();
	}

	@Override
	public String toString() {
		return String.format("<%.3f, %.3f, %.3f>", x, y, z);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}
}
