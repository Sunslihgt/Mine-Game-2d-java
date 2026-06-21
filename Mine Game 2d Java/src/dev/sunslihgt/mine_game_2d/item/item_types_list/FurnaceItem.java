package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.block.tile_entity.FurnaceTileEntity;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.PlaceableBlockItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class FurnaceItem extends PlaceableBlockItemType {

	public FurnaceItem(int id) {
		super(id, "chest", Assets.furnace_item, 64, ToolType.NONE, 0, 1, 0f, BlockType.furnaceBlock);
	}
}
