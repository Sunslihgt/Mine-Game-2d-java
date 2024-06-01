package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class CoalOreBlock extends BlockType {

	public CoalOreBlock(int id) {
		super(id, "coal ore", Assets.coal_ore_block, false, 0, true, false, 100, ToolType.PICKAXE, 1);
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.coalOreItem, 1, 1));
		super.blockDrops = drops;
	}
}
