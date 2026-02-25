package main.materials;

import main.math.vectors.Vector2;
import main.math.vectors.Vector3;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RadialTexture implements Material {

	private final BufferedImage image;
	private final MaterialData defaultMaterialData = new MaterialData();
	private final Vector2 offset;

	public RadialTexture(BufferedImage image) {
		this(image, new Vector2(0, 0));
	}

	public RadialTexture(BufferedImage image, Vector2 offset) {
		this.defaultMaterialData.albedo = Color.MAGENTA;
		this.image = image;
		this.offset = offset;
	}

	public Vector2 mapSphereToSquare(Vector3 position) {
		position = position.normalize();
		double u = .5 + (Math.atan2(position.z, position.x) / (2 * Math.PI));
		double v = 1 - (.5 + (Math.asin(position.y)) / Math.PI);
		return new Vector2(u, v);
	}

	@Override
	public MaterialData getMaterialData(Vector3 position, Vector3 rayDirection) {
		Vector2 uv = mapSphereToSquare(position).multiply(new Vector2(
				image.getWidth(),
				image.getHeight()
		)).toVector2();
		MaterialData newMaterialData = defaultMaterialData.copy();

		if (
				uv.xInt() < image.getWidth() && uv.yInt() < image.getHeight() &&
				uv.xInt() >= 0 && uv.yInt() >= 0
		)
			newMaterialData.albedo = new Color(image.getRGB(uv.xInt(), uv.yInt()));

		return newMaterialData;
	}
}
