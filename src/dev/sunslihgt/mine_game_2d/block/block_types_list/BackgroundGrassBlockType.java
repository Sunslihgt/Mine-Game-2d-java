package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class BackgroundGrassBlockType extends BlockType {

	public BackgroundGrassBlockType(int id) {
		super(id, "background grass", Assets.background_grass_block, false, Utils.VECTOR3_ZERO, false, true, 60, ToolType.SHOVEL, 0, false);
	}

	@Override
	protected void initDrops() {
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.grassItem, 1, 1));
		blockDrops = drops;
	}
}
