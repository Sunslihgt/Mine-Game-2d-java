package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class FurnaceBlock extends BlockType {

	public FurnaceBlock(int id) {
		super(id, "furnace", Assets.furnace_block_off, true, 0, false, false, 180, ToolType.PICKAXE, 0);
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.furnaceItem, 1, 1));
		super.blockDrops = drops;
	}
}
