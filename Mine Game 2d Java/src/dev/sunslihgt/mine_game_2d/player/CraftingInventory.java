package dev.sunslihgt.mine_game_2d.player;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.recipes.CraftingRecipe;
import dev.sunslihgt.mine_game_2d.recipes.CraftingRecipes;

public class CraftingInventory {
	private final int INVENTORY_X_OFFSET = PlayerInventory.INVENTORY_X_OFFSET, INVENTORY_Y_OFFSET = 270;
	private final int INVENTORY_WIDTH = 9 * (Inventory.INVENTORY_SCREEN_CELL_SIZE + Inventory.INVENTORY_SCREEN_MARGIN) - Inventory.INVENTORY_SCREEN_MARGIN + Inventory.INVENTORY_SCREEN_BORDER * 2;
	private final int INVENTORY_HEIGHT = 4 * (Inventory.INVENTORY_SCREEN_CELL_SIZE + Inventory.INVENTORY_SCREEN_MARGIN) - Inventory.INVENTORY_SCREEN_MARGIN + Inventory.INVENTORY_SCREEN_BORDER * 3;
	private final int CELL_SIZE = Inventory.INVENTORY_SCREEN_CELL_SIZE;
	private final int INVENTORY_MARGIN = Inventory.INVENTORY_SCREEN_MARGIN;
	private final int INVENTORY_BORDER = Inventory.INVENTORY_SCREEN_BORDER;
	
	private int currentCategoryIndex = 0;
	
	public CraftingInventory() {
		
	}
	
	// Render crafting inventory
	public void render(Graphics g, ArrayList<Item> availableItems) {
		// Background
		g.setColor(Inventory.INVENTORY_COLOR);
		g.fillRect(INVENTORY_X_OFFSET, INVENTORY_Y_OFFSET, INVENTORY_WIDTH, INVENTORY_HEIGHT);
		
		// Categories
		renderCategories(g);
		
		// Crafts
		renderCrafts(g, availableItems);
	}
	
	public void renderCategories(Graphics g) {
		for (int x = 0; x <= 9; x++) {
			if (x < 4) {
				// Selected category
				if (x == currentCategoryIndex) {
					g.setColor(Inventory.TOOLBAR_SELECTED_COLOR);
					int selectedX = INVENTORY_X_OFFSET + INVENTORY_BORDER + x * (CELL_SIZE + INVENTORY_MARGIN) - 4;
					int selectedY = INVENTORY_Y_OFFSET + INVENTORY_BORDER - 4;
					int selectedSize = CELL_SIZE + 8;
					g.fillRect(selectedX, selectedY, selectedSize, selectedSize);
				}
				// Cell
				int cellX = INVENTORY_X_OFFSET + INVENTORY_BORDER + x * (CELL_SIZE + INVENTORY_MARGIN);
				int cellY = INVENTORY_Y_OFFSET + INVENTORY_BORDER;
				
				g.setColor(Inventory.INVENTORY_CELL_COLOR);
				g.fillRect(cellX, cellY, CELL_SIZE, CELL_SIZE);
				
				int offset = (CELL_SIZE - Block.BLOCK_WIDTH) / 2;
				
				// Categories
				switch (x) {
					case 0: // All
						g.drawImage(Assets.grass_item, cellX + offset, cellY + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH, null);
						break;
					case 1: // Tools
						g.drawImage(Assets.stone_pickaxe_item, cellX + offset, cellY + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH, null);
						break;
					case 2: // Blocks
						g.drawImage(Assets.oak_plank_item, cellX + offset, cellY + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH, null);
						break;
					case 3: // Miscellaneous
						g.drawImage(Assets.stick_item, cellX + offset, cellY + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH, null);
						break;
					
					default:
						break;
				}
			}
		}
	}
	
	public void renderCrafts(Graphics g, ArrayList<Item> availableItems) {
		ArrayList<CraftingRecipe> availableCrafts = CraftingRecipes.getAvailableCraftsList(availableItems, currentCategoryIndex);
				
		for (int i = 0; i < 9 * 3; i++) {
			int slotX = i % 9;
			int slotY = Math.floorDiv(i, 9);
			
			// Cell
			int cellX = INVENTORY_X_OFFSET + INVENTORY_BORDER + slotX * (CELL_SIZE + INVENTORY_MARGIN);
			int cellY = INVENTORY_Y_OFFSET + INVENTORY_BORDER * 2 + CELL_SIZE + INVENTORY_MARGIN + slotY * (CELL_SIZE + INVENTORY_MARGIN);
			
			g.setColor(Inventory.INVENTORY_CELL_COLOR);
			g.fillRect(cellX, cellY, CELL_SIZE, CELL_SIZE);
			
			if (availableCrafts.size() > i) {
				int craftOffset = (CELL_SIZE - Block.BLOCK_WIDTH) / 2;
				int craftX = cellX + craftOffset;
				int craftY = cellY + craftOffset;
				
				Item craftItem = availableCrafts.get(i).getCraftedItem();
				Inventory.renderItem(g, craftX, craftY, craftItem, false);
			}
		}
	}
	
	// Check if the mouse is in the crafting inventory
	public boolean isMouseInInventory(int mX, int mY) {
		if (mX >= INVENTORY_X_OFFSET && mX <= INVENTORY_X_OFFSET + INVENTORY_WIDTH && mY >= INVENTORY_Y_OFFSET && mY <= INVENTORY_Y_OFFSET + INVENTORY_HEIGHT) {
			return true;
		}
		
		return false;
	}
	
	// Check if the mouse is in the crafting inventory
	public boolean clickCategory(int mX, int mY) {
		if (!isMouseInInventory(mX, mY)) {
			return false;
		}
		
		for (int i = 0; i < 4; i++) {
			int cellX = INVENTORY_X_OFFSET + INVENTORY_BORDER + i * (CELL_SIZE + INVENTORY_MARGIN);
			int cellY = INVENTORY_Y_OFFSET + INVENTORY_BORDER;
			if (mX >= cellX && mX <= cellX + CELL_SIZE && mY >= cellY && mY <= cellY + CELL_SIZE) {
				currentCategoryIndex = i;
				return true;
			}
		}
		
		return false;
	}
	
	// Get the slot hovered by the mouse (if none return -1)
	public CraftingRecipe getMouseHoveringCraft(int mX, int mY, ArrayList<Item> availableItems) {
		ArrayList<CraftingRecipe> availableCrafts = CraftingRecipes.getAvailableCraftsList(availableItems, currentCategoryIndex);
		
		for (int i = 0; i < availableCrafts.size(); i++) {
			int slotX = i % 9;
			int slotY = Math.floorDiv(i, 9);
			
			// Cell
			int cellX = INVENTORY_X_OFFSET + INVENTORY_BORDER + slotX * (CELL_SIZE + INVENTORY_MARGIN);
			int cellY = INVENTORY_Y_OFFSET + INVENTORY_BORDER * 2 + CELL_SIZE + INVENTORY_MARGIN + slotY * (CELL_SIZE + INVENTORY_MARGIN);
			
			if (mX >= cellX && mX <= cellX + CELL_SIZE && mY >= cellY && mY <= cellY + CELL_SIZE) {
				return availableCrafts.get(i);
			}
		}
		
		return null; // No slot hovered
	}
	
	// Consume crafting items and return true if everything is working
	public boolean consumeCraftItems(CraftingRecipe craft, Inventory inventory1, Inventory inventory2) {
		if (craft == null) {
			System.err.println("No craft provided in CraftingInventory.consumeCraftItems");
			return false;
		} else if (inventory1 == null) {
			System.err.println("No valid inventory1 provided in CraftingInventory.consumeCraftItems");
			return false;
		}
		
		ArrayList<Item> itemsCost = craft.getItemsCost();
		for (Item itemCost : itemsCost) {
			int itemCostCountRemaining = itemCost.getCount();
			
			int itemCountInventoy1 = inventory1.countItemId(itemCost.getId());
			int itemCountInventoy2 = 0;
			if (inventory2 != null) {
				itemCountInventoy2 = inventory2.countItemId(itemCost.getId());
			}
			
			if (itemCountInventoy1 >= itemCost.getCount()) {
				// Enough items in inventory 1 -> consume items
				if (!inventory1.removeItem(itemCost)) {
					System.err.println("Craft went wrong while consuming items in 1 inventory in CraftingInventory.consumeCraftItems");
					return false; // Something went wrong
				}
			} else if (itemCountInventoy1 + itemCountInventoy2 >= itemCost.getCount()) {
				// Enough items in both inventories -> consume items from both inventories
				int itemsConsumedInventory1 = Math.min(itemCountInventoy1, itemCostCountRemaining);
				int itemsConsumedInventory2 = Math.min(itemCountInventoy2, itemCostCountRemaining - itemCountInventoy1);
				
				if (!inventory1.removeItem(new Item(itemsConsumedInventory1, itemCost.getType())) || !inventory2.removeItem(new Item(itemsConsumedInventory2, itemCost.getType()))) {
					System.err.println("Craft went wrong while consuming items in 2 inventories in CraftingInventory.consumeCraftItems");
					return false;
				}
			} else {
				// Not enough items in inventories, craft is too expensive
				return false;
			}
		}
		
		// Craft finished successfully
		return true;
	}
	
}
