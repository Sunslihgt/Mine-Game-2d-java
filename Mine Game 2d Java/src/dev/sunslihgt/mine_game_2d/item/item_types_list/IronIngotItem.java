package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class IronIngotItem extends ItemType {

	public IronIngotItem(int id) {
		super(id, "iron ingot", Assets.iron_ingot_item, 64, ToolType.NONE, 0, 1, 0f);
	}
}
