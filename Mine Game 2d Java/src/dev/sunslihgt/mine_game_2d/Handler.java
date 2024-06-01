package dev.sunslihgt.mine_game_2d;

import dev.sunslihgt.mine_game_2d.gfx.GameCamera;
import dev.sunslihgt.mine_game_2d.input.KeyManager;
import dev.sunslihgt.mine_game_2d.input.MouseManager;
import dev.sunslihgt.mine_game_2d.player.Player;
import dev.sunslihgt.mine_game_2d.world.World;

public class Handler {

	private Game game;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}

	public Game getGame() {
		return game;
	}
	
	public World getWorld() {
		return game.getWorld();
	}

	public Player getPlayer() {
		return game.getPlayer();
	}

	public KeyManager getKeyboardManager() {
		return game.getKeyboardManager();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}
}
