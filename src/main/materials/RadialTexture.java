package main.materials;

import main.math.vectors.Vector2;
import main.math.vectors.Vector3;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RadialTexture implements Material {

	private final BufferedImage image;
	private final MaterialData defaultMaterialData;
	private final Vector2 offset;

	public RadialTexture(BufferedImage image) {
		this(image, new Vector2(0, 0));
	}

	public RadialTexture(BufferedImage image, Vector2 offset) {
		this(image, offset, new MaterialData(Color.MAGENTA));
	}

	public RadialTexture(BufferedImage image, Vector2 offset, MaterialData defaultMaterialData) {
		this.image = image;
		this.offset = offset;
		this.defaultMaterialData = defaultMaterialData;
	}

	public Vector2 mapSphereToSquare(Vector3 position) {
		position = position.normalize();
		double u = .5 + (Math.atan2(position.z, position.x) / (2 * Math.PI));
		double v = 1 - (.5 + (Math.asin(position.y)) / Math.PI);
		return new Vector2(u, v);
	}

	@Override
	public MaterialData getMaterialData(Vector3 position, Vector3 rayDirection) {
		Vector2 uv = mapSphereToSquare(position);

        uv = new Vector2(
                (uv.x + offset.x) % 1,
                (uv.y + offset.y) % 1
        );

        uv = uv.multiply(new Vector2(
                image.getWidth(),
                image.getHeight()
        )).toVector2();

		if (
				uv.xInt() < image.getWidth() && uv.yInt() < image.getHeight() &&
				uv.xInt() >= 0 && uv.yInt() >= 0
		)
			return new MaterialData(
					new Color(image.getRGB(uv.xInt(), uv.yInt())),
					defaultMaterialData.normalModifier(),
					defaultMaterialData.specularity(),
					defaultMaterialData.scattering(),
					defaultMaterialData.opacity(),
					defaultMaterialData.refractiveIndex()
			);

		return defaultMaterialData;
	}
}
