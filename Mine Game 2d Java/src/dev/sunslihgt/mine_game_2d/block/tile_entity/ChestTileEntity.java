package dev.sunslihgt.mine_game_2d.block.tile_entity;

import java.awt.Graphics;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.player.Inventory;
import dev.sunslihgt.mine_game_2d.player.PlayerInventory;
import dev.sunslihgt.mine_game_2d.player.PlayerInventory.OpenedInventoryEnum;

public class ChestTileEntity extends TileEntity {
	
	private final static int inventoryXOffset = 600, inventoryYOffset = 100;
	
	private Inventory chestInventory;
	
	private Handler handler;
	
	public ChestTileEntity(int x, int y, Handler handler) {
		super(x, y, BlockType.chestBlock);
		
		this.handler = handler;
		
		chestInventory = new Inventory(9, 3, false, inventoryXOffset, inventoryYOffset, 0);
	}
	
	@Override
	public boolean rightClickBlock() {
		// Open or close chest
		
		PlayerInventory playerInventory = handler.getPlayer().getPlayerInventory();
		
		if (playerInventory.getSelectedInventory() != OpenedInventoryEnum.CHEST || playerInventory.getChestSelected() != this) {
			playerInventory.setSelectedInventory(OpenedInventoryEnum.CHEST);
			playerInventory.setChestSelected(this); // Set as player's chest
			playerInventory.setFurnaceSelected(null); // Remove player's furnace
			playerInventory.setInventoryOpen(true); // Open inventory
		} else { // Close inventory and chest
			playerInventory.setInventoryOpen(false); // Close inventory
		}
		
		return true; // Prevent from using an item's right click action
	}
	
	// Called by the player when the chest is opened
	public void renderChestInventory(Graphics g) {
		// Chest is rendered only if opened
		chestInventory.render(g, true);
	}
	
	public Inventory getChestInventory() {
		return chestInventory;
	}
	
}
