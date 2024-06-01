package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class OakLeavesItem extends ItemType {

	public OakLeavesItem(int id) {
		super(id, "oak leaves", Assets.oak_leaves_item, 64, ToolType.NONE, 0, 1, 0f);
	}
	
	// Right click -> Place block
	public boolean rightClickAction(Handler handler) {
		int playerCursorX = handler.getPlayer().getPlayerCursor().getbX();
		int playerCursorY = handler.getPlayer().getPlayerCursor().getbY();
		
		// Place block
		if (handler.getWorld().isEmptyBlock(playerCursorX, playerCursorY) && !handler.getPlayer().checkCollisionBlockPos(playerCursorX, playerCursorY)) {
			Block block = new Block(playerCursorX, playerCursorY, BlockType.oakLeavesBlock);
			handler.getWorld().placeBlock(playerCursorX, playerCursorY, block);
			return true; // Consume item
		}
		
		return false; // Do not consume item
	}

}
