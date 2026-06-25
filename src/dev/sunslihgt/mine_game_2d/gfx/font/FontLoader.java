package dev.sunslihgt.mine_game_2d.gfx.font;

import com.raylib.Raylib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FontLoader {

	public static FontData loadFont(String path, int size) {
		try {
			File tempFile;
			try (var inputStream = FontLoader.class.getResourceAsStream(path)) {
				if (inputStream == null) {
					throw new IOException("Font resource not found: " + path);
				}

				String suffix = path.substring(path.lastIndexOf('.'));
				tempFile = java.io.File.createTempFile("asset_", suffix);
				tempFile.deleteOnExit();

				try (var out = new java.io.FileOutputStream(tempFile)) {
					inputStream.transferTo(out);
				}
			}

			return new FontData(
					Raylib.loadFontEx(tempFile.getAbsolutePath(), size, null, 0),
					size,
					0
			);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public static List<FontData> loadFonts(String path, List<Integer> sizes) {
		try {
			File tempFile;
			try (var inputStream = FontLoader.class.getResourceAsStream(path)) {
				if (inputStream == null) {
					throw new IOException("Font resource not found: " + path);
				}

				String suffix = path.substring(path.lastIndexOf('.'));
				tempFile = java.io.File.createTempFile("asset_", suffix);
				tempFile.deleteOnExit();

				try (var out = new java.io.FileOutputStream(tempFile)) {
					inputStream.transferTo(out);
				}
			}

			ArrayList<FontData> fontDataList = new ArrayList<>();
			for (int size : sizes) {
				fontDataList.add(new FontData(
					Raylib.loadFontEx(tempFile.getAbsolutePath(), size, null, 0),
					size,
					0
				));
			}
			return fontDataList;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
