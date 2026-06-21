package dev.sunslihgt.mine_game_2d.item;

import com.raylib.Texture;
import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.block.BlockFactory;
import dev.sunslihgt.mine_game_2d.block.BlockType;

import java.io.InvalidClassException;

public abstract class PlaceableBlockItemType extends ItemType {
	protected BlockType blockTypeToPlace;

	public PlaceableBlockItemType(int id, String name, Texture texture, int maxStack, ToolType toolType, int toolLvl, float toolEfficiency, float fuelBurnTime, BlockType blockToPlace) {
		super(id, name, texture, maxStack, toolType, toolLvl, toolEfficiency, fuelBurnTime);

		this.blockTypeToPlace = blockToPlace;
	}
	
	public boolean rightClickAction(Handler handler) {
		int playerCursorX = handler.getPlayer().getPlayerCursor().getbX();
		int playerCursorY = handler.getPlayer().getPlayerCursor().getbY();

		// Place block
		if (handler.getWorld().isEmptyBlock(playerCursorX, playerCursorY) && !handler.getPlayer().checkCollisionBlockPos(playerCursorX, playerCursorY)) {
			try {
				Block block = BlockFactory.createBlock(playerCursorX, playerCursorY, blockTypeToPlace, handler);
				handler.getWorld().placeBlock(playerCursorX, playerCursorY, block);
			} catch (InvalidClassException e) {
				e.printStackTrace();
				System.exit(1);
			}
			return true; // Consume item
		}

		return false; // Do not consume item
	}
}
