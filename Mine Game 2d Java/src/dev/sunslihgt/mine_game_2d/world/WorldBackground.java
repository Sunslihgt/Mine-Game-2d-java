package dev.sunslihgt.mine_game_2d.world;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import dev.sunslihgt.mine_game_2d.Handler;

public class WorldBackground {
	
	private ArrayList<Cloud> clouds = new ArrayList<>();
	private Handler handler;
	
	private final float cloudParallax = 0.04f;
	
	private int cloudsMinX, cloudsMaxX, maxClouds;
	private int screenLeftX, screenRightX;

	public WorldBackground(Handler handler) {
		this.handler = handler;
		
		createInitialClouds();
	}

	private void createInitialClouds() { // Spawn clouds inside screen
		Random r = new Random();
		
		maxClouds = 20 + r.nextInt(10);
		cloudsMinX = -500;
		cloudsMaxX = handler.getWidth() + 500;
		
		for (int i = 0; i < maxClouds - 2; i++) {
			float x = r.nextInt(cloudsMaxX - cloudsMinX) + cloudsMinX;
			float y = 10 + r.nextInt(400);
			float speed = 0.05f + r.nextFloat() * 0.2f;
			int size = r.nextInt(4);
			clouds.add(new Cloud(x, y, speed, size, cloudParallax, handler));
		}
	}
	
	public void spawnCloud() { // Spawn cloud outside screen
		Random r = new Random();
		
		float spawnX = 0;
		if (r.nextInt(10) < 7) { // Spawn left
			spawnX = r.nextFloat() * (screenLeftX - cloudsMinX) + cloudsMinX;
		} else { // Spawn right
			spawnX = r.nextInt(cloudsMaxX - screenRightX) + screenRightX;
		}
		
		
		float spawnY = 10 + r.nextInt((int) (handler.getHeight() * 0.3));
		float speed = 0.05f + r.nextFloat() * 0.2f;
		int size = r.nextInt(4);
		clouds.add(new Cloud(spawnX, spawnY, speed, size, cloudParallax, handler));
	}
	
	public void tick() {
		cloudsMinX = (int) (-500 + handler.getPlayer().getX() * cloudParallax);
		cloudsMaxX = (int) (handler.getWidth() + 500 + handler.getPlayer().getX() * cloudParallax);

		screenLeftX = (int) (handler.getPlayer().getX() * cloudParallax);
		screenRightX = (int) (handler.getPlayer().getX() * cloudParallax + handler.getWidth());
		
		for (Iterator<Cloud> iterator = clouds.iterator(); iterator.hasNext();) {
			Cloud cloud = (Cloud) iterator.next();
			cloud.tick();
			float cloudX = cloud.getX();
			
			if (cloudX < cloudsMinX || cloudX > cloudsMaxX) {
//				System.out.println("Cloud deleted");
				iterator.remove();
			}
		}
		
		if (maxClouds > clouds.size() && new Random().nextInt(10) > 8) {
			spawnCloud();
		}
		
//		System.out.println("Clouds: " + clouds.size() + "\t max clouds: " + maxClouds);
	}
	
	public void render(Graphics g) {
		// Debug
//		g.setColor(new Color(200, 100, 100));
//		g.fillRect((int) (cloudsMinX - handler.getPlayer().getX() * cloudParallax), 10, cloudsMaxX - cloudsMinX, 90);
//
//		g.setColor(new Color(100, 200, 100));
//		g.fillRect((int) (cloudsMinX - handler.getPlayer().getX() * cloudParallax) + 10, 30, screenLeftX - cloudsMinX, 90);
//
//		g.setColor(new Color(100, 100, 200));
//		g.fillRect((int) (screenRightX - handler.getPlayer().getX() * cloudParallax) - 10, 30, cloudsMaxX - screenRightX, 90);
		
		for (Cloud cloud : clouds) {
			cloud.render(g);
		}
	}
	
	
}
