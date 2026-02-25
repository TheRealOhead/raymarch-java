import main.gui.DummyImage;
import main.math.vectors.Vector2;
import scenes.SingleTriangle;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class DebugView {
	public static void main(String[] args) throws IOException {
		Vector2 size = new Vector2(800, 800);

		int threadCount = 32;

		int w = (int) size.x;
		int h = (int) size.y;

		BufferedImage product = Main.makeImageDisplayer("Render", 0, 0, w, h);
		BufferedImage depth = Main.makeImageDisplayer("Depth | Darker == Farther", w, 0, w, h);
		BufferedImage normals = Main.makeImageDisplayer("Normal | <x,y,z> -> <r,g,b>", 2 * w, 0, w, h);
		BufferedImage complexity = Main.makeImageDisplayer("Time | Darker == Longer", 0, h, w, h);
		BufferedImage albedo = Main.makeImageDisplayer("Albedo", w, h, w, h);
		BufferedImage normalModifications = Main.makeImageDisplayer("Normal modifications", 2 * w, h, w, h);
		BufferedImage stepsTaken = new DummyImage();//Main.makeImageDisplayer("Steps Taken | Darker == More", 2 * w, h, w, h);

		BufferedImage threads = new DummyImage();//Main.makeImageDisplayer("Color-coded by Thread", 0, 0, w, h);

		new SingleTriangle().getCamera().debugViews(
				product,
				albedo,
				depth,
				normals,
				threads,
				complexity,
				stepsTaken,
				normalModifications,

				threadCount,
				null
		);
	}
}
