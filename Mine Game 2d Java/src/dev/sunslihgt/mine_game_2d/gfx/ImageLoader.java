package dev.sunslihgt.mine_game_2d.gfx;

import com.raylib.Image;
import com.raylib.Raylib;
import com.raylib.Texture;

import java.io.IOException;


public class ImageLoader {
	public static Image loadImage(String path) {
		try {
			var inputStream = ImageLoader.class.getResourceAsStream(path);
			if (inputStream == null) {
				System.err.println("Resource not found: " + path);
				System.exit(1);
			}

			String suffix = path.substring(path.lastIndexOf('.'));
			java.io.File tempFile = java.io.File.createTempFile("asset_", suffix);
			tempFile.deleteOnExit();

			try (var out = new java.io.FileOutputStream(tempFile)) {
				inputStream.transferTo(out);
			}

			Image image = Raylib.loadImage(tempFile.getAbsolutePath());
			return image;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
