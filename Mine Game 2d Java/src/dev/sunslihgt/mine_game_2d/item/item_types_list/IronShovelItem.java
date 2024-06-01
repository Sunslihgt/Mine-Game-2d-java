package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class IronShovelItem extends ItemType {

	public IronShovelItem(int id) {
		super(id, "iron shovel", Assets.iron_shovel_item, 1, ToolType.SHOVEL, 3, 2.1f, 1f);
	}
}
