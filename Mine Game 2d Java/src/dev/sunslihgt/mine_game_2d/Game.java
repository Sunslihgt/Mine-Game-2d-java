package dev.sunslihgt.mine_game_2d;

import com.raylib.Color;
import com.raylib.Raylib;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.display.Display;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.gfx.GameCamera;
import dev.sunslihgt.mine_game_2d.input.KeyManager;
import dev.sunslihgt.mine_game_2d.input.MouseManager;
import dev.sunslihgt.mine_game_2d.player.Player;
import dev.sunslihgt.mine_game_2d.recipes.CookingRecipes;
import dev.sunslihgt.mine_game_2d.recipes.CraftingRecipes;
import dev.sunslihgt.mine_game_2d.utils.RaylibUtils;
import dev.sunslihgt.mine_game_2d.world.World;

public class Game {

	private int width, height;

	private boolean running = false;

	private final Color SKY_COLOR = RaylibUtils.createColor(135, 206, 235);

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
		display = new Display("Mine Game 2d Java - Sunslihgt", width, height);

		Assets.init();
		BlockType.initAllDrops();
		CookingRecipes.init();
		CraftingRecipes.init();
		handler = new Handler(this);
		player = new Player(0, 0, handler);
		gameCamera = new GameCamera(handler);
		world = new World(handler);
		world.spawnPlayer();
//		lighting = new Lighting(handler);
	}

	private void run() {
		init();

		final int fps = 60;
		double timePerTick = 1000000000 / fps;
		// double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		// long lastTickTime = System.nanoTime();

		while (running && !Raylib.windowShouldClose()) {
			now = System.nanoTime();
			// delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			// double deltaTickTime = (System.nanoTime() - lastTickTime); // Time since last tick (in nano sec)
			tick();
			render();
			ticks++;
			// delta--;
			// lastTickTime = System.nanoTime();

			if (timer >= 1_000_000_000) {
				System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
		}

		stop();
	}

	private void tick() {
		keyManager.tick();
		mouseManager.tick();
		player.tick();
		world.tick();
		
		gameCamera.calculateOffset();
	}

	private void render() {
//		long t = System.currentTimeMillis();

		Raylib.beginDrawing();

		// Fill Screen
		Raylib.clearBackground(SKY_COLOR);

		// Render
		world.render();
//		lighting.render(g);
		player.render();

		// Debug
		Raylib.drawFPS(width - 300, 100);

		// End Rendering
		Raylib.endDrawing();

//		System.out.println("rendering: " + (System.currentTimeMillis() - t));
	}

	public void start() {
		if (running)
			return;
		running = true;
		run();
	}

	public void stop() {
		if (!running)
			return;
		Raylib.closeWindow();
		running = false;
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
