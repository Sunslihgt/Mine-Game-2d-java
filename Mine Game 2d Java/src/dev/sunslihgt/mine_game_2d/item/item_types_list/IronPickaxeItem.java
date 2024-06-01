package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class IronPickaxeItem extends ItemType {

	public IronPickaxeItem(int id) {
		super(id, "iron pickaxe", Assets.iron_pickaxe_item, 1, ToolType.PICKAXE, 3, 2.8f, 1f);
	}
}
