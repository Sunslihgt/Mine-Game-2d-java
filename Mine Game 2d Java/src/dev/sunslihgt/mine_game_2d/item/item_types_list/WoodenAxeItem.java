package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class WoodenAxeItem extends ItemType {

	public WoodenAxeItem(int id) {
		super(id, "wooden axe", Assets.wooden_axe_item, 1, ToolType.AXE, 1, 1.2f, 1f);
	}
}
