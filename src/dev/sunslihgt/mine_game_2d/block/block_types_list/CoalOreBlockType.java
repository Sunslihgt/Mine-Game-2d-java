package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class CoalOreBlockType extends BlockType {

	public CoalOreBlockType(int id) {
		super(id, "coal ore", Assets.coal_ore_block, false, Utils.VECTOR3_ZERO, true, false, 100, ToolType.PICKAXE, 1, false);
	}

	@Override
	protected void initDrops() {
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.coalOreItem, 1, 1));
		blockDrops = drops;
	}
}
