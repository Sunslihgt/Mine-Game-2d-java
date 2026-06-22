package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class ChestBlockType extends BlockType {

	public ChestBlockType(int id) { // It does not contain everything about chests because it also has a tile entity
		super(id, "chest block", Assets.chest_block, true, Utils.VECTOR3_ZERO, false, false, 100, ToolType.AXE, 0, true);
	}

	@Override
	protected void initDrops() {
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.chestItem, 1, 1));
		blockDrops = drops;
	}
}
