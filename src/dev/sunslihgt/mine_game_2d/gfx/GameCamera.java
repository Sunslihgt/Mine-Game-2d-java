package dev.sunslihgt.mine_game_2d.gfx;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.player.Player;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class GameCamera {

	private final static int addedRenderDistance = 8;

	private int xOffset, yOffset;
	private int minRenderBX, minRenderBY;
	private final int renderBWidth, renderBHeight;
	private volatile GameCameraSnapshot snapshot;
	private final Handler handler;

	public GameCamera(Handler handler) {
		this.handler = handler;
		
		renderBWidth = Utils.convertPixelToBlock(handler.getWidth()) + addedRenderDistance * 2;
		renderBHeight = Utils.convertPixelToBlock(handler.getWidth()) + addedRenderDistance * 2;

		snapshot = new GameCameraSnapshot(0, 0, 0, 0, renderBWidth, renderBHeight);
	}

	public void calculateOffset() {
		// TODO: use Raylib's camera system
		Player player = handler.getPlayer();

		xOffset = (int) player.getX() - handler.game().getWidth() / 2;
		yOffset = (int) player.getY() - handler.game().getHeight() / 2;

		minRenderBX = Utils.convertPixelToBlock(xOffset) - addedRenderDistance;
		minRenderBY = Utils.convertPixelToBlock(yOffset) - addedRenderDistance;
		
		if (minRenderBY < 0) {
			minRenderBY = 0;
		}

		// Store the camera data for the current frame
		snapshot = new GameCameraSnapshot(xOffset, yOffset, minRenderBX, minRenderBY, renderBWidth, renderBHeight);

//		System.out.println("xOffset: " + xOffset + ", yOffset: " + yOffset);
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

	public GameCameraSnapshot getSnapshot() {
		return snapshot;
	}
}
