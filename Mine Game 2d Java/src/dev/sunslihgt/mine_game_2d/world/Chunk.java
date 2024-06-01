package dev.sunslihgt.mine_game_2d.world;

import java.awt.Graphics;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.utils.NoiseGenerator;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class Chunk {

	public final static int CHUNK_WIDTH = 16, CHUNK_HEIGHT = 128;
	public final static double NOISE_MIN = 0.35;
	
	private int chunkX;
	
	private int[] heightMap;
	private Block[][] blocks;
	private Block[][] backgroundBlocks;

	private Handler handler;

	public Chunk(Handler handler, int chunkX, double seed, double coalSeed, double ironSeed, double rubySeed) {
		this.handler = handler;
		this.chunkX = chunkX;

		createChunk(seed);
		populateChunk(coalSeed, ironSeed, rubySeed);
	}

	public void createChunk(double seed) {
		NoiseGenerator terrainNoiseGenerator = new NoiseGenerator(seed);

		heightMap = new int[CHUNK_WIDTH];
		backgroundBlocks = new Block[CHUNK_WIDTH][CHUNK_HEIGHT];
		blocks = new Block[CHUNK_WIDTH][CHUNK_HEIGHT];

		for (int cX = 0; cX < CHUNK_WIDTH; cX++) {
			int bX = Utils.convertToBlockX(cX, chunkX); // Get block coordinates

			/*
			 * Terrain Generation :
			 * 
			 * I am using a 1D noise to set the height
			 * and a 2D noise to generate caves.
			 * 
			 * Then, I am adding ores and trees
			 * using a different seed.
			 */

			// Noise
			int height = (int) (terrainNoiseGenerator.noise(bX * 0.9) * 10 - 5) + 64;
			heightMap[cX] = height;

			for (int y = 0; y < CHUNK_HEIGHT; y++) {
				double caves = (terrainNoiseGenerator.noise(bX, y) + 1) / 2;

				if (y < height) { // Too high -> Air
					backgroundBlocks[cX][y] = new Block(bX, y, BlockType.backgroundAirBlock);
					blocks[cX][y] = new Block(bX, y, BlockType.airBlock); // Air
				} else if (y == height) { // Grass
					backgroundBlocks[cX][y] = new Block(bX, y, BlockType.backgroundGrassBlock);
					if (caves > NOISE_MIN) {
						blocks[cX][y] = new Block(bX, y, BlockType.grassBlock);
					} else { // Cave
						blocks[cX][y] = new Block(bX, y, BlockType.airBlock);
					}
				} else if (y - 3 <= height) { // Dirt
					backgroundBlocks[cX][y] = new Block(bX, y, BlockType.backgroundDirtBlock);
					if (caves > NOISE_MIN) {
						blocks[cX][y] = new Block(bX, y, BlockType.dirtBlock);
					} else { // Cave
						blocks[cX][y] = new Block(bX, y, BlockType.airBlock);
					}
				} else { // Stone
					backgroundBlocks[cX][y] = new Block(bX, y, BlockType.backgroundStoneBlock);
					if (caves > NOISE_MIN) {
						blocks[cX][y] = new Block(bX, y, BlockType.stoneBlock);
					} else {
						blocks[cX][y] = new Block(bX, y, BlockType.airBlock);
					}
				}
			}
		}
	}
	
//	private void addTrees(int[] heights) {
//		for (int cX = 0; cX < CHUNK_WIDTH; cX++) {
//			int height = heights[cX];
//			if (blocks[cX][height] != null && blocks[cX][height].getType() == BlockType.grassBlock) {
//				if (Math.random() > 0.8) {
//					spawnTree(cX, height);
//				}
//			}
//		}
//	}
//	
//	private void spawnTree(int cX, int y) {
//		if (blocks[cX][y] != null && blocks[cX][y].getType() == BlockType.grassBlock) {
//			if (y - Tree.oakTree.getHeight() <= 0) {
//				return;
//			}
//			
//			
//		}
//	}
	
	public void populateChunk(double coalSeed, double ironSeed, double rubySeed) {
		NoiseGenerator coalGenerator = new NoiseGenerator(coalSeed);
		NoiseGenerator ironGenerator = new NoiseGenerator(ironSeed);
		NoiseGenerator rubyGenerator = new NoiseGenerator(rubySeed);

		for (int cX = 0; cX < CHUNK_WIDTH; cX++) {
			int bX = Utils.convertToBlockX(cX, chunkX); // Get block coordinates

			for (int y = 0; y < CHUNK_HEIGHT; y++) {
				if (blocks[cX][y] != null && blocks[cX][y].getId() == BlockType.stoneBlock.getId()) {
					// Noise
					double coalNoise = (coalGenerator.noise(bX * 3 + 6000, y * 3) + 1) / 2;
					double ironNoise = (ironGenerator.noise(bX * 3 - 2300, y * 3) + 1) / 2;
					double rubyNoise = (rubyGenerator.noise(bX * 3 + 3000 + 3 , y * 3) + 1) / 2;
					if (coalNoise > 0.8) {
						blocks[cX][y] = new Block(bX, y, BlockType.coalOreBlock);
					} else if (ironNoise > 0.8) {
						blocks[cX][y] = new Block(bX, y, BlockType.ironOreBlock);
					} else if (rubyNoise > 0.8) {
						blocks[cX][y] = new Block(bX, y, BlockType.rubyOreBlock);
					}
				}
			}
		}
	}

	public void tick() {
		for (int x = 0; x < CHUNK_WIDTH; x++) {
			for (int y = 0; y < CHUNK_HEIGHT; y++) {
				if (blocks[x][y] != null) {
					blocks[x][y].tick();
				}
			}
		}
	}

	public void render(Graphics g, int xOffset, int yOffset) {
		for (int x = 0; x < CHUNK_WIDTH; x++) {
			for (int y = 0; y < CHUNK_HEIGHT; y++) {
				if (blocks[x][y].getType().isTransparent()) {
					backgroundBlocks[x][y].render(g, xOffset, yOffset);
				}
				blocks[x][y].render(g, xOffset, yOffset);
			}
		}
	}

	public void resetLighting() {
		for (int x = 0; x < CHUNK_WIDTH; x++) {
			for (int y = 0; y < CHUNK_HEIGHT; y++) {
				if (blocks[x][y] != null) {
					blocks[x][y].setBlockLight(0);
					blocks[x][y].setSkyLight(0);
				}
			}
		}
	}
	
	public void setSkyLighting() {
		for (int x = 0; x < CHUNK_WIDTH; x++) {
			for (int y = 0; y < CHUNK_HEIGHT; y++) {
				if (blocks[x][y] != null) {
					if (y == 0 && blocks[x][y].getSkyLight() < 15) { // Spread sky light
						int bX = Utils.convertToBlockX(x, chunkX);
						handler.getWorld().spreadSkyLight(bX, y, 15);
					}
					
//					if (y == 0) {
//						blocks[x][y].setSkyLight(15);
//					} else if (y - 1 >= 0) {
//						if (blocks[x][y-1].getType().isTransparent()) {
//							blocks[x][y].setSkyLight(blocks[x][y-1].getSkyLight());
//						} else if (blocks[x][y-1].getSkyLight() > 1) {
//							blocks[x][y].setSkyLight(blocks[x][y-1].getSkyLight() - 1);
//						}
//					}
				}
			}
		}
	}
	
	public void setBlockLighting() {
		for (int x = 0; x < CHUNK_WIDTH; x++) {
			for (int y = 0; y < CHUNK_HEIGHT; y++) {
				if (blocks[x][y] != null) { // Spread block light
					if (blocks[x][y].getType().getLightEmited() > 0) {
						int bX = Utils.convertToBlockX(x, chunkX);
						handler.getWorld().spreadBlockLight(bX, y, blocks[x][y].getType().getLightEmited());
					}
				}
			}
		}
	}
	
	public int getPlayerSpawnHeight(int bX) {
		int chunkX = Utils.convertToChunkIndex(bX);
		int cX = Utils.convertToChunkBlockX(bX);
		if (this.chunkX == chunkX && cX >= 0 && cX < CHUNK_WIDTH) {
			for (int bY = 0; bY < CHUNK_HEIGHT; bY++) {
				if (blocks[cX][bY] != null && blocks[cX][bY].getType() != BlockType.airBlock) {
					return bY;
				}
			}
		}
		System.err.println("Block not found in Chunk.getPlayerSpawnHeight() with bX: " + bX + " (cX: " + cX + ")");
		return 0;
	}

	public Block getBlock(int bX, int bY) { // Get a block using block coordinates
		int chunkX = Utils.convertToChunkIndex(bX);
		int cX = Utils.convertToChunkBlockX(bX);
		if (this.chunkX == chunkX && cX >= 0 && cX < CHUNK_WIDTH && bY >= 0 && bY < CHUNK_HEIGHT) {
			return blocks[cX][bY];
		}
//		System.err.println("Block not found in Chunk.getBlock() with bX: " + bX + ", bY: " + bY + " (cX: " + cX + ")");
		return null;
	}

	public void placeBlock(int bX, int bY, Block block) {
		int chunkX = Utils.convertToChunkIndex(bX);
		int cX = Utils.convertToChunkBlockX(bX);

		if (this.chunkX == chunkX && cX >= 0 && cX < CHUNK_WIDTH && bY >= 0 && bY < CHUNK_HEIGHT) {
			blocks[cX][bY] = block;
		}
	}

	public void deleteBlock(int bX, int bY) {
		int chunkX = Utils.convertToChunkIndex(bX);
		int cX = Utils.convertToChunkBlockX(bX);

		if (this.chunkX == chunkX && cX >= 0 && cX < CHUNK_WIDTH && bY >= 0 && bY < CHUNK_HEIGHT) {
			blocks[cX][bY] = new Block(bX, bY, BlockType.airBlock);
		}
	}

	public void damageBlock(int bX, int bY, Item item) {
		int chunkX = Utils.convertToChunkIndex(bX);
		int cX = Utils.convertToChunkBlockX(bX);

		if (this.chunkX == chunkX && cX >= 0 && cX < CHUNK_WIDTH && bY >= 0 && bY < CHUNK_HEIGHT) {
			boolean blockBroken = blocks[cX][bY].damageBlock(item);
			if (blockBroken) {
				if (blocks[cX][bY].canItemHarvestBlock(item)) {
					handler.getPlayer().getPlayerInventory().addItemList(blocks[cX][bY].getDrops());
				}
				blocks[cX][bY] = new Block(bX, bY, BlockType.airBlock);
			}
		}
	}
	
	public Block getBackgroundBlock(int bX, int bY) { // Get a background block using block coordinates
		int chunkX = Utils.convertToChunkIndex(bX);
		int cX = Utils.convertToChunkBlockX(bX);
		if (this.chunkX == chunkX && cX >= 0 && cX < CHUNK_WIDTH && bY >= 0 && bY < CHUNK_HEIGHT) {
			return backgroundBlocks[cX][bY];
		}
//		System.err.println("Block not found in Chunk.getBackgroundBlock() with bX: " + bX + ", bY: " + bY + " (cX: " + cX + ")");
		return null;
	}
	
	public boolean isUnderground(int bX, int bY) {
		int chunkX = Utils.convertToChunkIndex(bX);
		int cX = Utils.convertToChunkBlockX(bX);
		if (this.chunkX == chunkX && cX >= 0 && cX < CHUNK_WIDTH && bY >= 0 && bY < CHUNK_HEIGHT) {
			return (heightMap[cX] <= bY);
		}
		return true;
	}

	public int getChunkX() {
		return chunkX;
	}
	
	public int[] getHeightMap() {
		return heightMap;
	}

}
