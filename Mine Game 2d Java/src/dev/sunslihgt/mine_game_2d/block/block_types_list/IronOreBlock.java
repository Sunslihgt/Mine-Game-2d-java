package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class IronOreBlock extends BlockType {

	public IronOreBlock(int id) {
		super(id, "iron ore", Assets.iron_ore_block, false, 0, true, false, 120, ToolType.PICKAXE, 2);
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.ironOreItem, 1, 1));
		super.blockDrops = drops;
	}
}
