package dev.sunslihgt.mine_game_2d.block.block_types_list;

import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.item.ToolType;
import dev.sunslihgt.mine_game_2d.utils.Utils;

public class BackgroundAirBlockType extends BlockType {

	public BackgroundAirBlockType(int id) {
		super(id, "background air", null, true, Utils.VECTOR3_ZERO, false, true, 0, ToolType.NONE, 0, false);
	}
}
