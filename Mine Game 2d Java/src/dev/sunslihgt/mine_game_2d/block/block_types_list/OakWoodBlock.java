package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class OakWoodBlock extends BlockType {

	public OakWoodBlock(int id) { // It does not contain everything about chests because it also has a tile entity
		super(id, "oak wood block", Assets.oak_wood_block, false, 0, true, false, 160, ToolType.AXE, 0);
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.oakWoodItem, 1, 1));
		super.blockDrops = drops;
	}
}
