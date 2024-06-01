package dev.sunslihgt.mine_game_2d.utils;

import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.world.Chunk;

public class Utils {
	// Block, Chunk and Chunk index conversion
	public static int convertToChunkIndex(int bX) {
		return Math.floorDiv(bX, Chunk.CHUNK_WIDTH);
	}
	
	public static int convertToBlockX(int cX, int chunkX) {
		return chunkX * Chunk.CHUNK_WIDTH + cX;
	}
	
	public static int convertToChunkBlockX(int bX) {
		int cX = bX % Chunk.CHUNK_WIDTH;
		if (cX < 0) {
			cX = 16 + cX;
		}
		return cX;
	}
	
	// Block and Pixel conversion
	public static int convertPixelToBlock(int pCoord) {
		return Math.floorDiv(pCoord, Block.BLOCK_WIDTH);
	}
	
	public static int convertPixelToBlock(float pCoord) {
		return convertPixelToBlock((int) pCoord);
	}
	
	public static int convertBlockToPixel(int bCoord) {
		return bCoord * Block.BLOCK_WIDTH;
	}
}
