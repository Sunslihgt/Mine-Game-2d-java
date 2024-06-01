package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class WoodenShovelItem extends ItemType {

	public WoodenShovelItem(int id) {
		super(id, "wooden shovel", Assets.wooden_shovel_item, 1, ToolType.SHOVEL, 1, 1.2f, 1f);
	}
}
