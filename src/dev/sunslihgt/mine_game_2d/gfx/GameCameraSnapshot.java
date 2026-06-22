package dev.sunslihgt.mine_game_2d.gfx;

/**
 * Stores GameCamera data for a single frame. Allows packing all the values to use in a different frame.
 * @param xOffset
 * @param yOffset
 * @param minRenderBX
 * @param minRenderBY
 * @param renderBWidth
 * @param renderBHeight
 */
public record GameCameraSnapshot(
        int xOffset,
        int yOffset,
        int minRenderBX,
        int minRenderBY,
        int renderBWidth,
        int renderBHeight
) {
}
