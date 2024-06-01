package dev.sunslihgt.mine_game_2d.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.gfx.Assets;

public class Cloud {

	private float x, y;
	private float speed;
	private int width, height;
	private float parallax;
	private BufferedImage texture;
	
	private Handler handler;
	
	public Cloud(float x, float y, float speed, int sizeIndex, float parallax, Handler handler) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.parallax = parallax;
		this.handler = handler;
		
		Random r = new Random();
		int id = r.nextInt(2);

		if (sizeIndex == 0) {
			texture = Assets.smallest_clouds[id];
			width = Assets.SMALL_CLOUD_WIDTH;
		} else if (sizeIndex == 1) {
			texture = Assets.small_clouds[id];
			width = Assets.SMALL_CLOUD_WIDTH;
		} else if (sizeIndex == 2) {
			texture = Assets.medium_clouds[id];
			width = Assets.MEDIUM_CLOUD_WIDTH;
		} else if (sizeIndex == 3) {
			texture = Assets.big_clouds[id];
			width = Assets.BIG_CLOUD_WIDTH;
		}
		height = Assets.CLOUD_HEIGHT;
		
	}
	
	public void tick() { // Move cloud
		x += speed;
	}
	
	public void render(Graphics g) {
		int renderX = (int) (x - handler.getPlayer().getX() * parallax);
		int renderY = (int) (y - handler.getPlayer().getY() * parallax * 2);
		g.drawImage(texture, renderX, renderY, width, height, null);
	}

	public float getX() {
		return x;
	}
	
	
}
