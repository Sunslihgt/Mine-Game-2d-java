package dev.sunslihgt.mine_game_2d.world.tree;

import dev.sunslihgt.mine_game_2d.block.BlockType;

public class OakTree extends Tree {

	public OakTree() {
		super(5, 5, new BlockType[][]
			{
				{null, BlockType.oakLeavesBlock, BlockType.oakLeavesBlock, null, null},
				{BlockType.oakLeavesBlock, BlockType.oakLeavesBlock, BlockType.oakLeavesBlock, null, null},
				{BlockType.oakLeavesBlock, BlockType.oakLeavesBlock, BlockType.oakWoodBlock, BlockType.oakWoodBlock, BlockType.oakWoodBlock},
				{BlockType.oakLeavesBlock, BlockType.oakLeavesBlock, BlockType.oakLeavesBlock, null, null},
				{null, BlockType.oakLeavesBlock, BlockType.oakLeavesBlock, null, null}
			}
		);
	}
	
	
}
