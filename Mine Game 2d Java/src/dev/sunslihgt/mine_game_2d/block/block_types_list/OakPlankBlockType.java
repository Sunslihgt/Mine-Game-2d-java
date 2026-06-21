package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class OakPlankBlockType extends BlockType {

	public OakPlankBlockType(int id) { // It does not contain everything about chests because it also has a tile entity
		super(id, "oak plank block", Assets.oak_plank_block, false, 0, true, false, 150, ToolType.AXE, 0, false);
	}

	@Override
	protected void initDrops() {
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.oakPlankItem, 1, 1));
		blockDrops = drops;
	}
}
