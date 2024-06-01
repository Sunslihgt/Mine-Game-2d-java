package dev.sunslihgt.mine_game_2d.gfx;

import java.awt.Color;
import java.awt.Graphics;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class Lighting {

	private static final int lightingFPS = 30;

	private long lastCalcul = -999;

	private LightingSolver lightingSolver;

	private Handler handler;

	public Lighting(Handler handler) {
		this.handler = handler;

		lightingSolver = new LightingSolver(handler);
		lightingSolver.setName("Lighting solver");
	}

	public void render(Graphics g) {
		if (System.currentTimeMillis() - lastCalcul > 1000 / lightingFPS && lightingSolver.calculsComplete) {
			lightingSolver.semaphore.release();
			lastCalcul = System.currentTimeMillis();
		}

		// Render light
		float[][][] lights = lightingSolver.getLights();
		int minRenderBX = lightingSolver.getMinRenderBX();
		int minRenderBY = lightingSolver.getMinRenderBY();
		int renderBWidth = lightingSolver.getRenderBWidth();
		int renderBHeight = lightingSolver.getRenderBHeight();

		for (int x = 0; x < renderBWidth; x++) {
			for (int y = 0; y < renderBHeight; y++) {
				int pX = Utils.convertBlockToPixel(x + minRenderBX) - handler.getGameCamera().getXOffset();
				int pY = Utils.convertBlockToPixel(y + minRenderBY) - handler.getGameCamera().getYOffset();

				try {
//					float lighting = (1 - lights[x][y]);
					float alpha = 1 - (lights[x][y][0] + lights[x][y][1] + lights[x][y][2]) / 3f;
					g.setColor(new Color(lights[x][y][0], lights[x][y][1], lights[x][y][2], alpha));
					g.fillRect(pX, pY, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH);
				} catch (NullPointerException e) {

				}
			}
		}
	}


}
