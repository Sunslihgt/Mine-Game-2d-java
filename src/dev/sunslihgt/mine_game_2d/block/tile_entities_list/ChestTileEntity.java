package dev.sunslihgt.mine_game_2d.block.tile_entities_list;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.block.TileEntity;
import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.player.Inventory;
import dev.sunslihgt.mine_game_2d.player.PlayerInventory;
import dev.sunslihgt.mine_game_2d.player.PlayerInventory.OpenedInventoryEnum;

import java.util.ArrayList;

public class ChestTileEntity extends TileEntity {
	
	private final static int inventoryXOffset = 600, inventoryYOffset = 100;
	
	private final Inventory chestInventory;
	
	private final Handler handler;
	
	public ChestTileEntity(int x, int y, Handler handler) {
		super(x, y, BlockType.chestBlock);
		
		this.handler = handler;
		
		chestInventory = new Inventory(9, 3, false, inventoryXOffset, inventoryYOffset, 0);
	}
	
	@Override
	public boolean rightClickBlock() {
		// Open or close chest on mouse just pressed
		if (!handler.getMouseManager().isRightJustPressed()) return false;

		PlayerInventory playerInventory = handler.getPlayer().getPlayerInventory();
		
		if (playerInventory.getSelectedInventory() != OpenedInventoryEnum.CHEST || playerInventory.getChestSelected() != this) {
			playerInventory.setInventoryOpen(true); // Open inventory
			playerInventory.setSelectedInventory(OpenedInventoryEnum.CHEST, this);
		} else { // Close chest
			playerInventory.setSelectedInventory(OpenedInventoryEnum.NONE, null);
		}
		
		return true; // Prevent from using an item's right click action
	}

	/**
	 * @return A list of items containing the block and the items in its inventory.
	 */
	@Override
	public ArrayList<Item> getDrops() {
		ArrayList<Item> drops = new ArrayList<Item>();

		// The block drop itself
		for (BlockDrop blockDrop : type.getBlockDrops()) {
			if (blockDrop != null) {
				drops.addAll(blockDrop.getDrops());
			}
		}

		// Add items in the container
		drops.addAll(chestInventory.getItemsListCopy());
		chestInventory.clearInventory();

		return drops;
	}

	// Called by the player when the chest is opened
	public void renderChestInventory() {
		// Chest is rendered only if opened
		chestInventory.render(true);
	}
	
	public Inventory getChestInventory() {
		return chestInventory;
	}
	
}
