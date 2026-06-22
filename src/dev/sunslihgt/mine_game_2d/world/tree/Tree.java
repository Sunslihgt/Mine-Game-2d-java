package dev.sunslihgt.mine_game_2d.world.tree;

import dev.sunslihgt.mine_game_2d.block.BlockType;

@SuppressWarnings("StaticInitializerReferencesSubClass")
public class Tree {
	
	public static final Tree oakTree = new OakTree();
	
	protected final int width, height;
	protected final BlockType[][] blocks;
	
	
	public Tree(int width, int height, BlockType[][] blocks) {
		this.width = width;
		this.height = height;
		this.blocks = blocks;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public BlockType[][] getBlocks() {
		return blocks;
	}
	
}
