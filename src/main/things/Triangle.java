package main.things;

import main.materials.Material;
import main.math.VarArgsMath;
import main.math.vectors.Vector3;

public class Triangle extends Thing {

	Vector3[] points;
	Material material;

	public Triangle(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 position, Vector3 rotation, Material material) {
		super(position, rotation);

		this.points = new Vector3[] {p1, p2, p3};

		this.material = material;
	}

	@Override
	public Material getMaterial() {
		return material;
	}


	/**
	 * Adapted from https://iquilezles.org/articles/distfunctions/
	 *
	 * float udTriangle( vec3 p, vec3 a, vec3 b, vec3 c )
	 * {
	 *   vec3 ba = b - a; vec3 pa = p - a;
	 *   vec3 cb = c - b; vec3 pb = p - b;
	 *   vec3 ac = a - c; vec3 pc = p - c;
	 *   vec3 nor = cross( ba, ac );
	 *
	 *   return sqrt(
	 *     (sign(dot(cross(ba,nor),pa)) +
	 *      sign(dot(cross(cb,nor),pb)) +
	 *      sign(dot(cross(ac,nor),pc))<2.0)
	 *      ?
	 *      min( min(
	 *      dot2(ba*clamp(dot(ba,pa)/dot2(ba),0.0,1.0)-pa),
	 *      dot2(cb*clamp(dot(cb,pb)/dot2(cb),0.0,1.0)-pb) ),
	 *      dot2(ac*clamp(dot(ac,pc)/dot2(ac),0.0,1.0)-pc) )
	 *      :
	 *      dot(nor,pa)*dot(nor,pa)/dot2(nor) );
	 * }
	 *
	 * @param position
	 * @return
	 */
	@Override
	public double sdf(Vector3 position) {
		Vector3 a = points[0];
		Vector3 b = points[1];
		Vector3 c = points[2];

		Vector3 ba = b.subtract(a); Vector3 pa = position.subtract(a);
		Vector3 cb = c.subtract(b); Vector3 pb = position.subtract(b);
		Vector3 ac = a.subtract(c); Vector3 pc = position.subtract(c);

		Vector3 normal = ba.crossProduct(ac);

		boolean edges =
				Math.signum(ba.crossProduct(normal).dotProduct(pa)) +
				Math.signum(cb.crossProduct(normal).dotProduct(pb)) +
				Math.signum(ac.crossProduct(normal).dotProduct(pc))
						< 2.0;

		// Edge closest
		if (edges) {
			return Math.sqrt(VarArgsMath.min(
					edgeSDF(ba, pa),
					edgeSDF(cb, pb),
					edgeSDF(ac, pc)
			));
		}

		// Face closest
		return Math.abs(normal.dotProduct(pa)) / normal.magnitude();
	}

	private static double edgeSDF(Vector3 ba, Vector3 pa) {
		return (ba.scale(Math.clamp(ba.dotProduct(pa) / ba.magnitudeSquared(), 0, 1))).subtract(pa).magnitudeSquared();
	}
}
