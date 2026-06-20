package dev.sunslihgt.mine_game_2d.utils;

import com.raylib.Color;
import com.raylib.Raylib;
import com.raylib.Rectangle;
import com.raylib.Texture;
import dev.sunslihgt.mine_game_2d.gfx.Assets;

public class RaylibUtils {
    // Colors
    public static Color CreateColor(int r, int g, int b) {
        return CreateColor(r, g, b, 0xFF);
    }

    public static Color CreateColor(int r, int g, int b, int a) {
        return new Color((byte) r, (byte) g, (byte) b, (byte) a);
    }

    public static Color CreateColor(int rgb) { // Hexadecimal int to color (rgb=0x123456 -> r=0x12, g=0x34, b=0x56)
        return new Color(
                (byte) (rgb >> 4),
                (byte) ((rgb >> 2) & 0xFF),
                (byte) (rgb & 0xFF),
                (byte) 0xFF
        );
    }

    // Texture
    public static Rectangle GetTextureRectangle(Texture texture) {
        return new Rectangle(
                0,
                0,
                texture.width(),
                texture.height()
        );
    }

    public static void draw(Texture texture, int x, int y, int width, int height) {
        draw(texture, Assets.defaultRectangle, new Rectangle(x, y, width, height));
    }

    public static void draw(Texture texture, Rectangle pos) {
        draw(texture, Assets.defaultRectangle, pos);
    }

    public static void draw(Texture texture, Rectangle textureRect, Rectangle pos) {
        Raylib.drawTexturePro(texture, textureRect, pos, Utils.VECTOR_ZERO, 0, Raylib.WHITE);
    }
}
