package dev.sunslihgt.mine_game_2d.gfx;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import com.raylib.Vector3;
import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class LightingSolver extends Thread {

	// Script inspired by Gonkee's really good video (youtube.com/watch?v=NEHMJwt7oUI)

	private static final int lightRadius = 20; // Distance used to calculate each light's emission. Does not define the light strength or intensity
	private static float blockLightDropOff = 0.70f, airLightDropOff = 0.93f;
	private static final float lightLevelCutOff = 0.01f; // The minimum value for light (if smaller -> 0)

	private static final float[] ambientColors = { 1f, 1f, 1f }; // Dark color

	private int newMinRenderBX, newMinRenderBY;
	private int renderBWidth, renderBHeight;
	private float[][][] lights;

	// Volatile
	public volatile LightSnapshot lightSnapshot;

	public final Semaphore semaphore = new Semaphore(1);

	private final Handler handler;

	private boolean running = true;

	public LightingSolver(Handler handler) {
		this.handler = handler;

		renderBWidth = handler.getGameCamera().getRenderBWidth();
		renderBHeight = handler.getGameCamera().getRenderBHeight();

		lights = new float[renderBWidth][renderBHeight][3];
		lightSnapshot = new LightSnapshot(lights, handler.getGameCamera().getSnapshot());

		start(); // Schedule the run method in another Thread
	}

	@Override
	public void run() {
		while (running) {
//			try {
//				semaphore.acquire(); // Wait for Lighting to release the semaphore
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

			// Get a snapshot of the game camera
			GameCameraSnapshot gameCameraSnapshot = handler.getGameCamera().getSnapshot();

			// Get the starting and width of the space to render
			newMinRenderBX = gameCameraSnapshot.minRenderBX();
			newMinRenderBY = gameCameraSnapshot.minRenderBY();

			renderBWidth = gameCameraSnapshot.renderBWidth();
			renderBHeight = gameCameraSnapshot.renderBHeight();

			if (renderBWidth <= 0 || renderBHeight <= 0) {
				return; // If there is no block to render return
			}
//			System.out.printf("Lighting solver (%d, %d, %d, %d)\n", newMinRenderBX, newMinRenderBY, newMinRenderBX + renderBWidth, newMinRenderBY + renderBHeight);

			// Calculate lighting for the frame
			calculateLighting();

			// Update the light data snapshot
			lightSnapshot = new LightSnapshot(lights, gameCameraSnapshot);
		}
	}

	private void calculateLighting() {
		boolean[][] emitsLight = new boolean[renderBWidth][renderBHeight];
		lights = new float[renderBWidth][renderBHeight][3];

		// Get a grid of emitting blocks (boolean)
		// Get a grid of light level (only for emitting blocks)
		for (int x = 0; x < renderBWidth; x++) {
			for (int y = 0; y < renderBHeight; y++) {
				Block block = handler.getWorld().getBlock(x + newMinRenderBX, y + newMinRenderBY);
				if (block == null) {
					emitsLight[x][y] = false;
					lights[x][y] = new float[] { 0, 0, 0 };
				} else {
					Vector3 blockEmittedLight = block.getType().getLightEmitted();
					if (Utils.magnitude(blockEmittedLight) > 0) {
						// Light block
						emitsLight[x][y] = true;
						lights[x][y] = new float[]{blockEmittedLight.getX(), blockEmittedLight.getY(), blockEmittedLight.getZ()};
					} else if (block.getType().isTransparent() && !handler.getWorld().isUnderground(x + newMinRenderBX, y + newMinRenderBY)) {
						// Outside air light
						emitsLight[x][y] = true;
						lights[x][y] = new float[] { ambientColors[0], ambientColors[1], ambientColors[2] };
					} else {
						// No light emitted
						emitsLight[x][y] = false;
						lights[x][y] = new float[] { 0, 0, 0 };
					}
				}
			}
		}

		// Emit light
		for (int x = 0; x < renderBWidth; x++) {
			for (int y = 0; y < renderBHeight; y++) {
				if (emitsLight[x][y]) {
					emitLight(x, y, lights[x][y]);
				}
			}
		}
	}

	private void emitLight(int startX, int startY, float[] light) {
		ArrayList<int[]> lightFillQueue = new ArrayList<int[]>();
		float[][][] singleLightEmission = new float[lightRadius * 2 + 1][lightRadius * 2 + 1][3];

		lightFillQueue.add(new int[] { startX, startY });
		singleLightEmission[lightRadius][lightRadius] = new float[] { light[0], light[1], light[2] };

		while (!lightFillQueue.isEmpty()) {
			int[] currentBlock = lightFillQueue.removeFirst();
			int x = currentBlock[0];
			int y = currentBlock[1];

			int distanceToStart = Utils.distance2d(x, y, startX, startY);

			boolean willPassOn = x == startX && y == startY; // True when at the start position
			float[] currentLight = lights[x][y];
			float[] targetLight = singleLightEmission[lightRadius + x - startX][lightRadius + y - startY];

			if ((targetLight[0] > lightLevelCutOff || targetLight[1] > lightLevelCutOff || targetLight[2] > lightLevelCutOff) && 
				(targetLight[0] > currentLight[0] || targetLight[1] > currentLight[1] || targetLight[2] > currentLight[2])) {
				// Merge the light levels by using the maximum for each color channel
				lights[x][y] = new float[] {
					Math.max(currentLight[0], targetLight[0]),
					Math.max(currentLight[1], targetLight[1]),
					Math.max(currentLight[2], targetLight[2])
				};
				willPassOn = true; // Run neighbors
			}

			// Spread to neighbors loop
			if (willPassOn) {
				for (int[] neighborOffset : Utils.neighbors4Ints) {
					int nx = x + neighborOffset[0];
					int ny = y + neighborOffset[1];

					if (nx >= 0 && nx < renderBWidth && ny >= 0 && ny < renderBHeight) {
						// In the render rect
						int neighborDistanceToStart = Utils.distance2d(nx, ny, startX, startY);
						if (neighborDistanceToStart > lightRadius) continue;

						float dropOff = blockLightDropOff;
						Block block = handler.getWorld().getBlock(x + newMinRenderBX, y + newMinRenderBY);
						if (block == null || block.getType().isTransparent()) {
							dropOff = airLightDropOff;
						}

						int emitX = lightRadius + nx - startX;
						int emitY = lightRadius + ny - startY;


						// Decrease light by multiplying by the dropOff coefficient
						float newR = Math.max(targetLight[0] - (1 - dropOff), singleLightEmission[emitX][emitY][0]);
						float newG = Math.max(targetLight[1] - (1 - dropOff), singleLightEmission[emitX][emitY][1]);
						float newB = Math.max(targetLight[2] - (1 - dropOff), singleLightEmission[emitX][emitY][2]);

						// Decrease light by subtracting by the dropOff
//						float newR = Math.max(targetLight[0] * dropOff, singleLightEmission[emitX][emitY][0]);
//						float newG = Math.max(targetLight[1] * dropOff, singleLightEmission[emitX][emitY][1]);
//						float newB = Math.max(targetLight[2] * dropOff, singleLightEmission[emitX][emitY][2]);

						// Add neighbor to the emit queue (if not emitted yet or any color channel has a higher value)
						if (
								neighborDistanceToStart == distanceToStart + 1 ||
								newR > singleLightEmission[emitX][emitY][0] ||
								newG > singleLightEmission[emitX][emitY][1] ||
								newB > singleLightEmission[emitX][emitY][2]
						) {
							lightFillQueue.add(new int[] { nx, ny });
						}

						singleLightEmission[emitX][emitY][0] = newR;
						singleLightEmission[emitX][emitY][1] = newG;
						singleLightEmission[emitX][emitY][2] = newB;
					}
				}
			}
		}
	}

	public LightSnapshot getLightSnapshot() {
		return lightSnapshot;
	}

//	public static void incrementBlockLightDropOff(float amount) {
//		blockLightDropOff = Math.clamp(blockLightDropOff + amount, 0, 1f);
//		System.out.printf("Light drop off: air=%f, block=%f\n", airLightDropOff, blockLightDropOff);
//	}
//
//	public static void incrementAirLightDropOff(float amount) {
//		airLightDropOff = Math.clamp(airLightDropOff + amount, 0, 1f);
//		System.out.printf("Light drop off: air=%f, block=%f\n", airLightDropOff, blockLightDropOff);
//	}

	public void destroy() {
		running = false;
	}
}
