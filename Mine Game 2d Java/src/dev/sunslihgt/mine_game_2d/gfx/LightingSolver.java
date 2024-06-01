package dev.sunslihgt.mine_game_2d.gfx;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.block.BlockType;

public class LightingSolver extends Thread {

	// Script inspired by Gonkee's really good video (youtube.com/watch?v=NEHMJwt7oUI)

	private static final int lightRadius = 10;
	private static final float blockLightDropOff = 0.7f, airLightDropOff = 0.9f;
	private static final float lightLevelCutOff = 0.01f; // The minimum value for light (if smaller -> 0)

	private static final float[] ambientColors = { 1f, 1f, 1f };

	private int newMinRenderBX, newMinRenderBY;
	private int minRenderBX, minRenderBY;
	private int renderBWidth, renderBHeight;

	@SuppressWarnings("unused")
	private boolean[][] emitsLight;
	private float[][][] lights, calculatingLights;

	boolean calculsComplete = true;

	Semaphore semaphore = new Semaphore(6);

	private Handler handler;

	public LightingSolver(Handler handler) {
		this.handler = handler;

		renderBWidth = handler.getGameCamera().getRenderBWidth();
		renderBHeight = handler.getGameCamera().getRenderBHeight();

		lights = new float[renderBWidth][renderBHeight][3];

		start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			calculsComplete = false;

			// Get the starting and width of the space to render
			newMinRenderBX = handler.getGameCamera().getMinRenderBX();
			newMinRenderBY = handler.getGameCamera().getMinRenderBY();

			renderBWidth = handler.getGameCamera().getRenderBWidth();
			renderBHeight = handler.getGameCamera().getRenderBHeight();

			if (renderBWidth <= 0 || renderBHeight <= 0) {
				return; // If there is no block to render return
			}

			// Calculate lighting
			calculateLighting();

			minRenderBX = newMinRenderBX;
			minRenderBY = newMinRenderBY;

			calculsComplete = true;
		}
	}

	private void calculateLighting() {
		boolean[][] emitsLight = new boolean[renderBWidth][renderBHeight];
		calculatingLights = new float[renderBWidth][renderBHeight][3];

//		int[][][] lightColors = new int[renderWidth][renderHeight][3];

		// Get a grid of emitting blocks (boolean)
		// Get a grid of light level (only for emitting blocks)
		for (int x = 0; x < renderBWidth; x++) {
			for (int y = 0; y < renderBHeight; y++) {
				Block block = handler.getWorld().getBlock(x + newMinRenderBX, y + newMinRenderBY);
				if (block == null) {
					emitsLight[x][y] = false;
					calculatingLights[x][y] = new float[] { 0, 0, 0 };
				} else if (block.getId() == BlockType.airBlock.getId()) {
					if (!handler.getWorld().isUnderground(x + newMinRenderBX, y + newMinRenderBY)) {
						emitsLight[x][y] = true;
						calculatingLights[x][y] = new float[] { ambientColors[0], ambientColors[1], ambientColors[2] };
					} else {
						emitsLight[x][y] = false;
						calculatingLights[x][y] = new float[] { 0, 0, 0 };
					}
				} else if (block.getType().getLightEmited() > 0) {
					emitsLight[x][y] = true;
					calculatingLights[x][y] = new float[] { 1f, 1f, 0 };
				} else {
					emitsLight[x][y] = false;
					calculatingLights[x][y] = new float[] { 0, 0, 0 };
				}
			}
		}

		// Emit light
		for (int x = 0; x < renderBWidth; x++) {
			for (int y = 0; y < renderBHeight; y++) {
				if (emitsLight[x][y]) {
					emitLight(x, y, calculatingLights[x][y]);
				}
			}
		}

		lights = calculatingLights;
	}

	private void emitLight(int startX, int startY, float[] light) {
		ArrayList<int[]> lightFillQueue = new ArrayList<int[]>();
		float[][][] singleLightEmission = new float[lightRadius * 2 + 1][lightRadius * 2 + 1][1];
		for (int x = 0; x < lightRadius * 2 + 1; x++) {
			for (int y = 0; y < lightRadius * 2 + 1; y++) {
				singleLightEmission[x][y] = new float[] { 0, 0, 0 };
			}
		}

		lightFillQueue.add(new int[] { startX, startY });
		singleLightEmission[lightRadius][lightRadius] = new float[] { light[0], light[1], light[2] };

		while (!lightFillQueue.isEmpty()) {
			int[] currentBlock = lightFillQueue.remove(0);
			int x = currentBlock[0];
			int y = currentBlock[1];

			int currentLayer = Math.abs(x - startX) + Math.abs(y - startY);

			boolean willPassOn = false;
			float[] currentLight = calculatingLights[x][y];
			float[] targetLight = singleLightEmission[lightRadius + x - startX][lightRadius + y - startY];

			if ((targetLight[0] > lightLevelCutOff || targetLight[1] > lightLevelCutOff || targetLight[2] > lightLevelCutOff) && 
				(targetLight[0] > currentLight[0] || targetLight[1] > currentLight[1] || targetLight[2] > currentLight[2])) {
				calculatingLights[x][y] = new float[] {
					targetLight[0],
					targetLight[1],
					targetLight[2]
				};
				willPassOn = true;
			}

			// Neighbor loop
			if (willPassOn || (x == startX && y == startY)) {
				for (int nx = x - 1; nx <= x + 1; nx++) {
					for (int ny = y - 1; ny <= y + 1; ny++) {
						if (nx >= 0 && nx < renderBWidth && ny >= 0 && ny < renderBHeight && (nx != x || ny != y)) {
							int neighborLayer = Math.abs(nx - startX) + Math.abs(ny - startY);
							if (neighborLayer <= lightRadius && neighborLayer == currentLayer + 1) {
								
								float dropOff = blockLightDropOff;
								if (handler.getWorld().getBlock(x, y).getId() != BlockType.airBlock.getId() || handler.getWorld().getBlock(x, y).getType().isTransparent()) {
									dropOff = airLightDropOff;
								}

								int emitX = lightRadius + nx - startX;
								int emitY = lightRadius + ny - startY;

								if (singleLightEmission[emitX][emitY][0] + singleLightEmission[emitX][emitY][1] + singleLightEmission[emitX][emitY][2] == 0) {
									lightFillQueue.add(new int[] { nx, ny });
								}

								singleLightEmission[emitX][emitY][0] = Math.max(targetLight[0] * dropOff, singleLightEmission[emitX][emitY][0]);
								singleLightEmission[emitX][emitY][1] = Math.max(targetLight[1] * dropOff, singleLightEmission[emitX][emitY][1]);
								singleLightEmission[emitX][emitY][2] = Math.max(targetLight[2] * dropOff, singleLightEmission[emitX][emitY][2]);
							}
						}

					}
				}
			}
		}


	}

	public float[][][] getLights() {
		return lights.clone();
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
