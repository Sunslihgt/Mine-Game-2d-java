package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class StoneShovelItem extends ItemType {

	public StoneShovelItem(int id) {
		super(id, "stone shovel", Assets.stone_shovel_item, 1, ToolType.SHOVEL, 2, 1.6f, 1f);
	}
}
