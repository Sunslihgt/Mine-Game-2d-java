package dev.sunslihgt.mine_game_2d.gfx;

import com.raylib.Color;
import com.raylib.Font;
import com.raylib.Raylib;
import com.raylib.Vector2;

public class Text {
	
	public static void drawString(String text, int xPos, int yPos, boolean center, boolean alignRight, Color c, Font font) {
		int fontSize = 16; // TODO: get font size
		int spacing = 1; // TODO: get font spacing
		int x = xPos;
		int y = yPos;
		if (center) {
			Vector2 textSize = Raylib.measureTextEx(font, text, fontSize, 1); // TODO: Fix measureTextEx always returns zero
			x = (int) (xPos - textSize.getX() / 2);
			y = (int) (yPos - textSize.getY() / 2);
		}
		if (alignRight) {
			Vector2 textSize = Raylib.measureTextEx(font, text, fontSize, 1); // TODO: Fix measureTextEx always returns zero
//			System.out.println("drawString align right x=" + x + ", textSizeX=" + textSize.getX() + ", drawX=" + (int) (xPos - textSize.getX()));
			x = (int) (xPos - textSize.getX());
		}
		Raylib.drawTextEx(font, text, new Vector2(x, y), fontSize, spacing, c);
	}
}
