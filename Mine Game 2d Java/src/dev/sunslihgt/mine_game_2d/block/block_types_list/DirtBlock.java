package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class DirtBlock extends BlockType {

	public DirtBlock(int id) {
		super(id, "dirt", Assets.dirt_block, false, 0, true, false, 80, ToolType.SHOVEL, 0);
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.dirtItem, 1, 1));
		super.blockDrops = drops;
	}
}
