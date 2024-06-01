package dev.sunslihgt.mine_game_2d.block.block_types_list;

import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class AirBlock extends BlockType {

	public AirBlock(int id) {
		super(id, "air", null, true, 0, false, false, 0, ToolType.NONE, 0);
	}
}
