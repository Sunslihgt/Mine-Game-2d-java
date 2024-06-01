package dev.sunslihgt.mine_game_2d.world;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.utils.Utils;
import dev.sunslihgt.mine_game_2d.world.tree.Tree;

public class World {
	private ArrayList<Chunk> chunks;
	private ArrayList<Integer> chunkXList;

	private WorldBackground background;

	// Seeds
	private double terrainSeed;
	private double coalSeed, ironSeed, rubySeed;

	private Handler handler;

	public World(Handler handler) {
		this.handler = handler;
		background = new WorldBackground(handler);
		createChunks();
	}

	private void createChunks() {
		terrainSeed = Math.random();
		coalSeed = Math.random();
		ironSeed = Math.random();
		rubySeed = Math.random();
		System.out.println("Seed Terrain: " + terrainSeed);

		chunks = new ArrayList<Chunk>();
		chunkXList = new ArrayList<Integer>();
		for (int x = -10; x <= 10; x++) {
			spawnChunk(x);
		}

	}

	private void spawnChunk(int x) {
		Chunk chunk = new Chunk(handler, x, terrainSeed, coalSeed, ironSeed, rubySeed);
		chunks.add(chunk);
		chunkXList.add(x);
		addTrees(chunk);
	}

	private void addTrees(Chunk chunk) {
		for (int cX = 0; cX < Chunk.CHUNK_WIDTH; cX++) {
			int height = chunk.getHeightMap()[cX];
			int bX = Utils.convertToBlockX(cX, chunk.getChunkX());
			if (chunk.getBlock(bX, height) != null && chunk.getBlock(bX, height).getType() == BlockType.grassBlock) {
				if (Math.random() > 0.8) {
					spawnTree(bX, height - 1);
				}
			}
		}
	}

	private void spawnTree(int spawnBX, int spawnBY) {
		Tree tree = Tree.oakTree; // Should be randomized based on biome
		int treeWidth = tree.getWidth();
		int treeHeight = tree.getHeight();

		int topY = spawnBY - treeHeight + 1;

		int leftX = spawnBX - (int) (Math.floor((float) treeWidth / 2));
		int rightX = spawnBX + (int) (Math.floor((float) treeWidth / 2));

		// Check that the tree isn't outside the world
		if (spawnBY < Chunk.CHUNK_HEIGHT && topY >= 0) {
			if (hasChunkBlockBeenGenerated(leftX) && hasChunkBlockBeenGenerated(rightX)) {
				// Check if there is enough room
				boolean obstacle = false;
				for (int bX = leftX; bX < rightX; bX++) {
					for (int bY = spawnBY; bY >= topY; bY--) {
						Block block = getBlock(bX, bY);
						if (block == null || block.getId() != BlockType.airBlock.getId()) {
							obstacle = true;
							break;
						}
					}
					if (obstacle) {
						break;
					}
				}

				// Eventually, spawn the tree
				if (!obstacle) {
					System.out.println("Spawn the tree");
					BlockType[][] treeBlocksTypes = tree.getBlocks();

					for (int x = 0; x < treeWidth; x++) {
						int bX = leftX + x;
						for (int y = 0; y < treeHeight; y++) {
							int bY = topY + y;
							if (treeBlocksTypes[x][y] != null) {
								placeBlock(bX, bY, new Block(bX, bY, treeBlocksTypes[x][y]));
							}
						}
					}
				}
			}
		}
	}

	public void tick() {
		background.tick();

		int minChunkX = (int) ((handler.getPlayer().getX() - handler.getWidth() / 2) / (Block.BLOCK_WIDTH * Chunk.CHUNK_WIDTH)) - 1;
		int maxChunkX = (int) ((handler.getPlayer().getX() + handler.getWidth() / 2) / (Block.BLOCK_WIDTH * Chunk.CHUNK_WIDTH));

		// Tick chunks
		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() >= minChunkX && chunk.getChunkX() <= maxChunkX) {
				chunk.tick();
			}
		}
	}

	public void render(Graphics g) {
		background.render(g);

		int xOffset = handler.getGameCamera().getXOffset();
		int yOffset = handler.getGameCamera().getYOffset();

		int minChunkX = (int) ((handler.getPlayer().getX() - handler.getWidth() / 2) / (Block.BLOCK_WIDTH * Chunk.CHUNK_WIDTH)) - 1;
		int maxChunkX = (int) ((handler.getPlayer().getX() + handler.getWidth() / 2) / (Block.BLOCK_WIDTH * Chunk.CHUNK_WIDTH));

		// If a chunk has not been generated, generate it
		for (int x = minChunkX; x <= maxChunkX; x++) {
			if (!chunkXList.contains(x)) {
				spawnChunk(x);
			}
		}

		// Lighting
//		resetLighting(minChunkX, maxChunkX);
//		skyLighting(minChunkX, maxChunkX);
//		blockLighting(minChunkX, maxChunkX);

		// Render chunks
		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() >= minChunkX && chunk.getChunkX() <= maxChunkX) {
				chunk.render(g, xOffset, yOffset);
			}
		}
	}

	@SuppressWarnings("unused")
	private void resetLighting(int minChunkX, int maxChunkX) {
		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() >= minChunkX && chunk.getChunkX() <= maxChunkX) {
				chunk.resetLighting();
			}
		}
	}

	@SuppressWarnings("unused")
	private void skyLighting(int minChunkX, int maxChunkX) {
		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() >= minChunkX && chunk.getChunkX() <= maxChunkX) {
				chunk.setSkyLighting();
			}
		}
	}

	public void spreadSkyLight(int bX, int bY, int light) {
		if (light > 0) {
			Block block = getBlock(bX, bY);
			if (block != null && block.getSkyLight() < light) {
				if (block.getType().isTransparent()) {
					if (block.getSkyLight() < light) {
						getBlock(bX, bY).setSkyLight(light);

						// Spread light
						if (bY + 1 < Chunk.CHUNK_HEIGHT) { // Down
							spreadSkyLight(bX, bY + 1, light);
						}
						spreadSkyLight(bX - 1, bY, light - 1); // Left
						spreadSkyLight(bX + 1, bY, light - 1); // Right
					}
				} else {
					light--;
					if (block.getSkyLight() < light) {
						getBlock(bX, bY).setSkyLight(light);

						// Spread light
						if (bY + 1 < Chunk.CHUNK_HEIGHT) { // Down
							spreadSkyLight(bX, bY + 1, light);
						}
						spreadSkyLight(bX - 1, bY, light - 1); // Left
						spreadSkyLight(bX + 1, bY, light - 1); // Right
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void blockLighting(int minChunkX, int maxChunkX) {
		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() >= minChunkX && chunk.getChunkX() <= maxChunkX) {
				chunk.setBlockLighting();
			}
		}
	}

	public void spreadBlockLight(int bX, int bY, int light) {
		if (light > 0) {
			Block block = getBlock(bX, bY);
			if (block != null && block.getBlockLight() < light) {
				getBlock(bX, bY).setBlockLight(light);

				// Spread light
				if (bY - 1 >= 0) { // Up
					spreadBlockLight(bX, bY - 1, light - 1);
				}
				if (bY + 1 < Chunk.CHUNK_HEIGHT) { // Down
					spreadBlockLight(bX, bY + 1, light - 1);
				}
				spreadBlockLight(bX - 1, bY, light - 1); // Left
				spreadBlockLight(bX + 1, bY, light - 1); // Right
			}

		}
	}

	public void spawnPlayer() {
		int bX = 0;
		int bY = 0;

		int chunkX = Utils.convertToChunkIndex(bX);
		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() == chunkX) {
				bY = chunk.getPlayerSpawnHeight(bX);
			}
		}

		float pX = Utils.convertBlockToPixel(bX) + Block.BLOCK_WIDTH / 2;
		float pY = Utils.convertBlockToPixel(bY) - 0.01f;
		handler.getPlayer().setX(pX);
		handler.getPlayer().setY(pY);

	}

	public Block getBlock(int bX, int bY) {
		int chunkX = Utils.convertToChunkIndex(bX);

		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() == chunkX) {
				return chunk.getBlock(bX, bY);
			}
		}

//		System.err.println("Chunk not found in World.getBlock with bX: " + bX + " -> chunkX: " + chunkX);
		return null;
	}

	public void placeBlock(int bX, int bY, Block block) {
		int chunkX = Utils.convertToChunkIndex(bX);

		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() == chunkX) {
				chunk.placeBlock(bX, bY, block);
				return;
			}
		}

		System.err.println("Chunk not found in World.deleteBlock with bX: " + bX + " -> chunkX: " + chunkX);
	}

	public void deleteBlock(int bX, int bY) {
		int chunkX = Utils.convertToChunkIndex(bX);

		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() == chunkX) {
				chunk.deleteBlock(bX, bY);
				return;
			}
		}

		System.err.println("Chunk not found in World.deleteBlock with bX: " + bX + " -> chunkX: " + chunkX);
	}

	public void damageBlock(int bX, int bY, Item item) {
		int chunkX = Utils.convertToChunkIndex(bX);

		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() == chunkX) {
				chunk.damageBlock(bX, bY, item);
				return;
			}
		}
	}

	public boolean isEmptyBlock(int bX, int bY) {
		Block block = getBlock(bX, bY);
		return (block != null && block.getId() == BlockType.airBlock.getId());
	}

	// Return true if a chunk has been generated given a block x coordinate
	public boolean hasChunkBlockBeenGenerated(int bX) {
		int chunkIndex = Utils.convertToChunkIndex(bX);
		return chunkXList.contains(chunkIndex);
	}

	public Block getBackgroundBlock(int bX, int bY) {
		int chunkX = Utils.convertToChunkIndex(bX);

		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() == chunkX) {
				return chunk.getBackgroundBlock(bX, bY);
			}
		}

//		System.err.println("Chunk not found in World.getBackgroundBlock with bX: " + bX + " -> chunkX: " + chunkX);
		return null;
	}

	public boolean isUnderground(int bX, int bY) {
		int chunkX = Utils.convertToChunkIndex(bX);

		for (Chunk chunk : chunks) {
			if (chunk.getChunkX() == chunkX) {
				return chunk.isUnderground(bX, bY);
			}
		}
		return true;
	}

	public boolean rightClickBlock(int bX, int bY) {
		Block block = getBlock(bX, bY);

		if (block != null) {
			return block.rightClickBlock();
		}
		return false;
	}

}
