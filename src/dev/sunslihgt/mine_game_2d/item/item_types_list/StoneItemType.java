package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.PlaceableBlockItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class StoneItemType extends PlaceableBlockItemType {

	public StoneItemType(int id) {
		super(id, "stone", Assets.stone_item, 64, ToolType.NONE, 0, 1, 0f, BlockType.stoneBlock);
	}

}
