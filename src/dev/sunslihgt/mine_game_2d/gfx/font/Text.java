package dev.sunslihgt.mine_game_2d.gfx.font;

import com.raylib.Color;
import com.raylib.Font;
import com.raylib.Raylib;
import com.raylib.Vector2;

public class Text {
	public static final int INVENTORY_SIZE = 25, DEFAULT_SIZE = 20;

	public enum FontAnchor {
		TOP_LEFT(0x11),
		TOP_CENTER(0x12),
		TOP_RIGHT(0x13),
		MIDDLE_LEFT(0x21),
		MIDDLE_CENTER(0x22),
		MIDDLE_RIGHT(0x23),
		BOTTOM_LEFT(0x31),
		BOTTOM_CENTER(0x32),
		BOTTOM_RIGHT(0x33);

		public final int value;

		FontAnchor(int value) {
			this.value = value;
		}
	}

	public static void drawString(String text, int xPos, int yPos, FontData fontData, FontAnchor anchor, Color c) {
		drawString(text, xPos, yPos, fontData.fontSize(), fontData.spacing(), anchor, c, fontData.font());
	}

	public static void drawString(String text, int xPos, int yPos, int fontSize, int spacing, FontAnchor anchor, Color c, Font font) {
		Vector2 textSize = Raylib.measureTextEx(font, text, fontSize, spacing);
		if ((anchor.value % 0x10) == 2) { // Horizontal center
			xPos = (int) (xPos - textSize.getX() / 2);
		} else if ((anchor.value % 0x10) == 3) { // Horizontal right
			xPos = (int) (xPos - textSize.getX());
		}

		if ((anchor.value >> 4) == 2) {
			yPos = (int) (yPos - textSize.getY() / 2);
		} else if ((anchor.value >> 4) == 3) {
			yPos = (int) (yPos - textSize.getY());
		}

//		Raylib.drawRectangle(xPos, yPos, (int) textSize.getX(), (int) textSize.getY(), Raylib.RED); // Debug textSize rectangle
		Raylib.drawTextEx(font, text, new Vector2(xPos, yPos), fontSize, spacing, c);
	}
}
