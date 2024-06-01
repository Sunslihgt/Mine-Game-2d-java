package dev.sunslihgt.mine_game_2d.player;

import java.awt.Graphics;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class PlayerCursor {

	private int bX = 0, bY = 0;
	private boolean visible = true;
	
	private Handler handler;
	
	public PlayerCursor(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() {
		bX = Utils.convertPixelToBlock(handler.getMouseManager().getMouseX() + handler.getGameCamera().getXOffset());
		bY = Utils.convertPixelToBlock(handler.getMouseManager().getMouseY() + handler.getGameCamera().getYOffset());
		
		visible = !handler.getPlayer().getPlayerInventory().isMouseInInventory() && handler.getMouseManager().isMouseInScreen();
		
//		System.out.println("Mouse bX: " + bX + ", bY: " + bY);
//		System.out.println(visible + ", mouse inv: " + handler.getPlayer().getPlayerInventory().isMouseInInventory() + ", mouse screen: " + handler.getMouseManager().isMouseInScreen());
	}
	
	public void render(Graphics g) {
		// Do not render if mouse in inventory
		if (!visible) {
			return;
		}
		
		int x = Utils.convertBlockToPixel(bX) - handler.getGameCamera().getXOffset();
		int y = Utils.convertBlockToPixel(bY) - handler.getGameCamera().getYOffset();

		g.drawImage(Assets.selectedBlockOutline, x, y, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH, null);
	}

	public int getbX() {
		return bX;
	}

	public int getbY() {
		return bY;
	}
	
	public boolean isCursorVisible() {
		return visible;
	}
	
}
