package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class ChestBlock extends BlockType {

	public ChestBlock(int id) { // It does not contain everything about chests because it also has a tile entity
		super(id, "chest block", Assets.chest_block, true, 0, false, false, 100, ToolType.AXE, 0);
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.chestItem, 1, 1));
		super.blockDrops = drops;
	}
}
