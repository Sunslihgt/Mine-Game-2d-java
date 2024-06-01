package dev.sunslihgt.mine_game_2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.sunslihgt.mine_game_2d.display.Display;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.gfx.GameCamera;
import dev.sunslihgt.mine_game_2d.input.KeyManager;
import dev.sunslihgt.mine_game_2d.input.MouseManager;
import dev.sunslihgt.mine_game_2d.player.Player;
import dev.sunslihgt.mine_game_2d.recipes.CookingRecipes;
import dev.sunslihgt.mine_game_2d.recipes.CraftingRecipes;
import dev.sunslihgt.mine_game_2d.world.World;

public class Game implements Runnable {

	private int width, height;

	private boolean running = false;
	private Thread thread;

	private BufferStrategy bs;
	private Graphics g;
	
	private final Color SKY_COLOR = new Color(135, 206, 235);

	// Display
	private Display display;

	// Handler
	private Handler handler;

	// World
	private World world;
	
	// Lighting
//	private Lighting lighting;

	// Player
	private Player player;
	
	// Game Camera
	private GameCamera gameCamera;
	
	// Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	public Game(int width, int height) {
		this.width = width;
		this.height = height;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}

	private void init() {
		Assets.init();
		CookingRecipes.init();
		CraftingRecipes.init();
		display = new Display("Mine Game 2d Java - Sunslihgt", width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getFrame().addMouseWheelListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		handler = new Handler(this);
		player = new Player(96, 0, handler);
		gameCamera = new GameCamera(handler);
		world = new World(handler);
		world.spawnPlayer();
//		lighting = new Lighting(handler);
	}

	public void run() {
		init();

		final int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		@SuppressWarnings("unused")
		int ticks = 0;
//		long lastTickTime = System.nanoTime();

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if (delta >= 1) {
//				double deltaTickTime = (System.nanoTime() - lastTickTime); // Time since last tick (in nano sec)
				tick();
				render();
				ticks++;
				delta--;
//				lastTickTime = System.nanoTime();
			}

			if (timer >= 1000000000) {
//				System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}

		stop();
	}

	private void tick() {
		keyManager.tick();
		player.tick();
		world.tick();
		
		gameCamera.calculateOffset();
	}

	private void render() {
//		long t = System.currentTimeMillis();
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		// Fill Screen
		g.setColor(SKY_COLOR);
		g.fillRect(0, 0, width, height);
		
		
		// Render
		world.render(g);
//		lighting.render(g);
		player.render(g);
		
		
		// End Rendering
		bs.show();
		g.dispose();
//		System.out.println("rendering: " + (System.currentTimeMillis() - t));
	}
	
	// Thread
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	// GETTERS AND SETTERS
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public World getWorld() {
		return world;
	}

	public Player getPlayer() {
		return player;
	}

	public KeyManager getKeyboardManager() {
		return keyManager;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

}
