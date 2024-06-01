package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class CoalItem extends ItemType {

	public CoalItem(int id) {
		super(id, "coal", Assets.coal_item, 64, ToolType.NONE, 0, 1, 8f);
	}
}
