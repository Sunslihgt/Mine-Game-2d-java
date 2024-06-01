package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class IronAxeItem extends ItemType {

	public IronAxeItem(int id) {
		super(id, "iron axe", Assets.iron_axe_item, 1, ToolType.AXE, 3, 2.1f, 1f);
	}
}
