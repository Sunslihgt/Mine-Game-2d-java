package dev.sunslihgt.mine_game_2d.gfx;

import com.raylib.Color;
import com.raylib.Raylib;
import com.raylib.Raylib.BlendMode;
import com.raylib.RenderTexture;
import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.utils.RaylibUtils;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class Lighting {
	private final Color darkColor = RaylibUtils.createColor(5, 5, 10, 255);
	private int blendModeIndex = 3;
	int[] blendModes = new int[] {
			BlendMode.BLEND_ALPHA,
			BlendMode.BLEND_ADDITIVE,
			BlendMode.BLEND_ADD_COLORS,
//			BlendMode.BLEND_SUBTRACT_COLORS,
//			BlendMode.BLEND_MULTIPLIED,
			BlendMode.BLEND_ALPHA_PREMULTIPLY,
//			BlendMode.BLEND_CUSTOM,
//			BlendMode.BLEND_CUSTOM_SEPARATE,
	};
	String[] blendModeNames = new String[] {
			"BLEND_ALPHA",
			"BLEND_ADDITIVE",
			"BLEND_ADD_COLORS",
//			"BLEND_SUBTRACT_COLORS",
//			"BLEND_MULTIPLIED",
			"BLEND_ALPHA_PREMULTIPLY",
//			"BLEND_CUSTOM",
//			"BLEND_CUSTOM_SEPARATE",
	};
	private boolean debugLightBuffer = false;

	private final LightingSolver lightingSolver;

	private final Handler handler;

	public Lighting(Handler handler) {
		this.handler = handler;

		lightingSolver = new LightingSolver(handler);
		lightingSolver.setName("Lighting solver");
	}

	public void render() {
		if (lightingSolver.semaphore.availablePermits() == 0) {
			lightingSolver.semaphore.release();
		}

		RenderTexture lightBuffer = handler.getDisplay().getScreenLightTexture();
		Raylib.beginTextureMode(lightBuffer);
			Raylib.clearBackground(darkColor); // Ambient darkness
			Raylib.beginBlendMode(blendModes[blendModeIndex]);

				// Get a LightSnapshot from the LightSolver
				LightSnapshot lightSnapshot = lightingSolver.getLightSnapshot();

				// Render light
				float[][][] lights = lightSnapshot.lights(); // Can change each frame
				int minRenderBX = lightSnapshot.gameCameraSnapshot().minRenderBX();
				int minRenderBY = lightSnapshot.gameCameraSnapshot().minRenderBY();
				int renderBWidth = lightSnapshot.gameCameraSnapshot().renderBWidth();
				int renderBHeight = lightSnapshot.gameCameraSnapshot().renderBHeight();
//				System.out.printf(
//						"Lighting BX=%d, BY=%d, bWidth=%d, bHeight=%d, xOffset=%d, yOffset=%d\n",
//						minRenderBX, minRenderBY, lightingXOffset, lightingYOffset, renderBWidth, renderBHeight
//				);

				for (int x = 0; x < renderBWidth; x++) {
					for (int y = 0; y < renderBHeight; y++) {
						int pX = Utils.convertBlockToPixel(x + minRenderBX) - handler.getGameCamera().getXOffset();
						int pY = Utils.convertBlockToPixel(y + minRenderBY) - handler.getGameCamera().getYOffset();
						pY = handler.getHeight() - pY - Block.BLOCK_WIDTH; // Flip y-axis for Raylib (openGL)

						float alpha = Utils.maxFloat(0f, lights[x][y][0], lights[x][y][1], lights[x][y][2]);
						Raylib.drawRectangle(
								pX, pY, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH,
								RaylibUtils.createColorFloat(lights[x][y][0], lights[x][y][1], lights[x][y][2], alpha)
						);
					}
				}

			// Stop painting on the buffer
			Raylib.endBlendMode();
		Raylib.endTextureMode();

		// Add the buffer to the screen
		if (!debugLightBuffer) {
			Raylib.beginBlendMode(BlendMode.BLEND_MULTIPLIED);
		}
			Raylib.drawTextureRec(
				lightBuffer.texture(),
				handler.getDisplay().getScreenRect(),
				Utils.VECTOR2_ZERO,
				Raylib.WHITE
			);
		if (!debugLightBuffer) {
			Raylib.endBlendMode();
		}
	}

	public void destroy() {
		lightingSolver.destroy();
	}

//	public void incrementBlendModeIndex(int incr) {
//		blendModeIndex += incr;
//		if (blendModeIndex < 0) blendModeIndex += blendModes.length;
//		blendModeIndex %= blendModes.length;
//
//		System.out.println("Blend mode=" + blendModeNames[blendModeIndex]);
//	}
//
//	public void toggleDebugLightBuffer() {
//		debugLightBuffer = !debugLightBuffer;
//	}
}
