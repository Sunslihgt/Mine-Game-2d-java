package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class StoneBlock extends BlockType {

	public StoneBlock(int id) {
		super(id, "stone", Assets.stone_block, false, 0, true, false, 200, ToolType.PICKAXE, 1);
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.stoneItem, 1, 1));
		super.blockDrops = drops;
	}
}
