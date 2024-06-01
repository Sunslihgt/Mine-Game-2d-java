package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class StonePickaxeItem extends ItemType {

	public StonePickaxeItem(int id) {
		super(id, "stone pickaxe", Assets.stone_pickaxe_item, 1, ToolType.PICKAXE, 2, 2.2f, 1f);
	}
}
