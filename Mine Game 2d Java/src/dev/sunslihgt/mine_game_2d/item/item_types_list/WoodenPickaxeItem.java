package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class WoodenPickaxeItem extends ItemType {

	public WoodenPickaxeItem(int id) {
		super(id, "wooden pickaxe", Assets.wooden_pickaxe_item, 1, ToolType.PICKAXE, 1, 1.8f, 1f);
	}
}
