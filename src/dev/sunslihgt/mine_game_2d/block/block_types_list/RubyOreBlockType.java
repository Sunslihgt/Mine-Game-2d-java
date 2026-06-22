package dev.sunslihgt.mine_game_2d.block.block_types_list;

import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class RubyOreBlockType extends BlockType {

	public RubyOreBlockType(int id) {
		super(id, "ruby ore", Assets.ruby_ore_block, false, Utils.VECTOR3_ZERO, true, false, 140, ToolType.PICKAXE, 3, false);
	}

	@Override
	protected void initDrops() {
		ArrayList<BlockDrop> drops = new ArrayList<>();
		drops.add(new BlockDrop(ItemType.rubyOreItem, 1, 1));
		blockDrops = drops;
	}
}
