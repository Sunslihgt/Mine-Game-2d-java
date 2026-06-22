package dev.sunslihgt.mine_game_2d.gfx;

import com.raylib.Image;
import com.raylib.Rectangle;
import com.raylib.Texture;
import com.raylib.Raylib;


public class SpriteSheet {

	private final Image sheet;
	private boolean unloaded = false;
	
	public SpriteSheet(Image sheet){
		this.sheet = sheet;
	}

	public Texture crop(int x, int y, int width, int height) throws Exception {
		if (unloaded) {
			throw new Exception("Cannot crop Sprite Sheet because it was already unloaded");
		}

		var cropRect = new Rectangle(x, y, width, height);
		Image cropped = Raylib.imageFromImage(sheet, cropRect); // CPU subimage
		Texture texture = Raylib.loadTextureFromImage(cropped); // GPU texture
		Raylib.unloadImage(cropped); // Free CPU subimage
		return texture;
	}

	/**
	 * Unloads the spritesheet
	 */
	public void unload() {
		Raylib.unloadImage(sheet);
		unloaded = true;
	}
}
