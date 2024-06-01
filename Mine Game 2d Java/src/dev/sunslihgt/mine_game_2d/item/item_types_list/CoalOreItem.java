package dev.sunslihgt.mine_game_2d.item.item_types_list;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.item.ToolType;

public class CoalOreItem extends ItemType {

	public CoalOreItem(int id) {
		super(id, "coal ore", Assets.coal_ore_item, 64, ToolType.NONE, 0, 1, 0f);
	}

	// Right click -> Place block
	public boolean rightClickAction(Handler handler) {
		int playerCursorX = handler.getPlayer().getPlayerCursor().getbX();
		int playerCursorY = handler.getPlayer().getPlayerCursor().getbY();

		// Place block
		if (handler.getWorld().isEmptyBlock(playerCursorX, playerCursorY) && !handler.getPlayer().checkCollisionBlockPos(playerCursorX, playerCursorY)) {
			Block dirtBlock = new Block(playerCursorX, playerCursorY, BlockType.coalOreBlock);
			handler.getWorld().placeBlock(playerCursorX, playerCursorY, dirtBlock);
			return true; // Consume item
		}

		return false; // Do not consume item
	}
}
