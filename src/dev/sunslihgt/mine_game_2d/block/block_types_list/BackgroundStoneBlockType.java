package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class BackgroundStoneBlockType extends BlockType {

	public BackgroundStoneBlockType(int id) {
		super(id, "background stone", Assets.background_stone_block, false, Utils.VECTOR3_ZERO, false, true, 200, ToolType.PICKAXE, 1, false);
	}

	@Override
	protected void initDrops() {
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.stoneItem, 1, 1));
		blockDrops = drops;
	}
}
