package dev.sunslihgt.mine_game_2d.gfx;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.player.Player;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class GameCamera {

	private final static int addedRenderDistance = 8;

	private int xOffset, yOffset;
	private int minRenderBX, minRenderBY;
	private int renderBWidth, renderBHeight;
	private Handler handler;

	public GameCamera(Handler handler) {
		this.handler = handler;
		
		renderBWidth = Utils.convertPixelToBlock(handler.getWidth()) + addedRenderDistance * 2;
		renderBHeight = Utils.convertPixelToBlock(handler.getWidth()) + addedRenderDistance * 2;
	}

	public void calculateOffset() {
		Player player = handler.getPlayer();

		xOffset = (int) (player.getX() - handler.getGame().getWidth() / 2);
		yOffset = (int) (player.getY() - handler.getGame().getHeight() / 2);

		minRenderBX = Utils.convertPixelToBlock(xOffset) - addedRenderDistance;
		minRenderBY = Utils.convertPixelToBlock(yOffset) - addedRenderDistance;
		
		if (minRenderBY < 0) {
			minRenderBY = 0;
		}

//		System.out.println("xOffset: " + xOffset + ", yOffset: " + yOffset);

//		int mBX = handler.getPlayer().getPlayerCursor().getbX();
//		int mBY = handler.getPlayer().getPlayerCursor().getbY();
//		System.out.println("rMinBX: " + renderMinBX + ", rMinBY: " + renderMinBY + ", rMaxBX: " + renderMaxBX + ", rMaxBY: " + renderMaxBY + ", mBX: " + mBX + ", mBY: " + mBY);
	}

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	public int getMinRenderBX() {
		return minRenderBX;
	}

	public int getMinRenderBY() {
		return minRenderBY;
	}

	public int getRenderBWidth() {
		return renderBWidth;
	}

	public int getRenderBHeight() {
		return renderBHeight;
	}
}
