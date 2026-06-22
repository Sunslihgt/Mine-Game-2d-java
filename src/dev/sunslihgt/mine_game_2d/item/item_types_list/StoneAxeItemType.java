package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class StoneAxeItemType extends ItemType {

	public StoneAxeItemType(int id) {
		super(id, "stone axe", Assets.stone_axe_item, 1, ToolType.AXE, 2, 1.6f, 1f);
	}
}
