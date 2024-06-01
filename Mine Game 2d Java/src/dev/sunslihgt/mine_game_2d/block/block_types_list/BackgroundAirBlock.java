package dev.sunslihgt.mine_game_2d.block.block_types_list;

import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class BackgroundAirBlock extends BlockType {

	public BackgroundAirBlock(int id) {
		super(id, "background air", null, true, 0, false, true, 0, ToolType.NONE, 0);
	}
}
