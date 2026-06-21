package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class BackgroundDirtBlockType extends BlockType {

	public BackgroundDirtBlockType(int id) {
		super(id, "background dirt", Assets.background_dirt_block, false, 0, false, true, 60, ToolType.SHOVEL, 0, false);
	}

	@Override
	protected void initDrops() {
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.dirtItem, 1, 1));
		blockDrops = drops;
	}
}
