package dev.sunslihgt.mine_game_2d.world;

import java.util.Random;

import com.raylib.Raylib;
import com.raylib.Rectangle;
import com.raylib.Texture;
import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.utils.RaylibUtils;

public class Cloud {

	private float x, y;
	private float speed;
	private int width, height;
	private float parallax;
	private Texture texture;
	private Rectangle textureRect;
	
	private Handler handler;
	
	public Cloud(float x, float y, float speed, int sizeIndex, float parallax, Handler handler) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.parallax = parallax;
		this.handler = handler;
		
		Random r = new Random();
		int id = r.nextInt(2);

        switch (sizeIndex) {
            case 0 -> {
                texture = Assets.smallest_clouds[id];
                width = Assets.SMALL_CLOUD_WIDTH;
                textureRect = Assets.SMALLEST_CLOUD_RECT;
            }
            case 1 -> {
                texture = Assets.small_clouds[id];
                width = Assets.SMALL_CLOUD_WIDTH;
                textureRect = Assets.SMALL_CLOUD_RECT;
            }
            case 2 -> {
                texture = Assets.medium_clouds[id];
                width = Assets.MEDIUM_CLOUD_WIDTH;
                textureRect = Assets.MEDIUM_CLOUD_RECT;
            }
            case 3 -> {
                texture = Assets.big_clouds[id];
                width = Assets.BIG_CLOUD_WIDTH;
                textureRect = Assets.BIG_CLOUD_RECT;
            }
            default -> {
				texture = Assets.small_clouds[id];
				width = Assets.SMALL_CLOUD_WIDTH;
				textureRect = Assets.SMALL_CLOUD_RECT;
            }
        }
		height = Assets.CLOUD_HEIGHT;
		
	}
	
	public void tick() { // Move cloud
		x += speed;
	}
	
	public void render() {
		int renderX = (int) (x - handler.getPlayer().getX() * parallax);
		int renderY = (int) (y - handler.getPlayer().getY() * parallax * 2);
		Rectangle posRect = new Rectangle(renderX, renderY, width, height);
		RaylibUtils.draw(texture, textureRect, posRect);
	}

	public float getX() {
		return x;
	}
	
	
}
