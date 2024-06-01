package dev.sunslihgt.mine_game_2d.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Text {
	
	public static void drawString(Graphics g, String text, int xPos, int yPos, boolean center, boolean alignRight, Color c, Font font) {
		g.setColor(c);
		g.setFont(font);
		int x = xPos;
		int y = yPos;
		if(center) {
			FontMetrics fm = g.getFontMetrics(font);
			x = xPos - fm.stringWidth(text) / 2;
			y = (yPos - fm.getHeight() / 2) + fm.getAscent();
		}
		if (alignRight) {
			FontMetrics fm = g.getFontMetrics(font);
			x = xPos - fm.stringWidth(text);
		}
		g.drawString(text, x, y);
	}
}
