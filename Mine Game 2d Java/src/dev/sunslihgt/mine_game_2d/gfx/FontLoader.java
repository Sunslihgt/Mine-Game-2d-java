package dev.sunslihgt.mine_game_2d.gfx;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {

	public static Font loadFont(String path, float size) {
		try (InputStream is = FontLoader.class.getResourceAsStream(path)) {
			if (is == null) {
				throw new IOException("Font resource not found on classpath: " + path);
			}
			return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

}
