package dev.sunslihgt.mine_game_2d.gfx.lighting;

import dev.sunslihgt.mine_game_2d.gfx.GameCameraSnapshot;

/**
 * Stores light data for a single frame and the necessary parameters to render it on top of the scene in a different thread.
 * @param lights Light values calculated
 * @param gameCameraSnapshot GameCamera data for the frame
 */
public record LightSnapshot(
        float[][][] lights,
        GameCameraSnapshot gameCameraSnapshot
) {
}
