package dev.sunslihgt.mine_game_2d.player;

import java.util.ArrayList;

import com.raylib.Raylib;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.recipes.CraftingRecipe;
import dev.sunslihgt.mine_game_2d.recipes.CraftingRecipes;
import dev.sunslihgt.mine_game_2d.utils.RaylibUtils;

public class CraftingInventory {
	private final int INVENTORY_X_OFFSET = PlayerInventory.INVENTORY_X_OFFSET, INVENTORY_Y_OFFSET = 270;
	private final int INVENTORY_WIDTH = 9 * (Inventory.INVENTORY_SCREEN_CELL_SIZE + Inventory.INVENTORY_SCREEN_MARGIN) - Inventory.INVENTORY_SCREEN_MARGIN + Inventory.INVENTORY_SCREEN_BORDER * 2;
	private final int INVENTORY_HEIGHT = 4 * (Inventory.INVENTORY_SCREEN_CELL_SIZE + Inventory.INVENTORY_SCREEN_MARGIN) - Inventory.INVENTORY_SCREEN_MARGIN + Inventory.INVENTORY_SCREEN_BORDER * 3;
	private final int CELL_SIZE = Inventory.INVENTORY_SCREEN_CELL_SIZE;
	private final int INVENTORY_MARGIN = Inventory.INVENTORY_SCREEN_MARGIN;
	private final int INVENTORY_BORDER = Inventory.INVENTORY_SCREEN_BORDER;

	private final int CATEGORY_COUNT = 4;
	private int currentCategoryIndex = 0;

	private ArrayList<CraftingRecipe> categoryAvailableCrafts;

	/**
	 * Update the local list of available crafts given available items. Run before and after crafting.
	 * @param availableItems List of the available items. Same item types must be merged together even if it exceeds the maxCount.
	 */
	public void updateAvailableCrafts(ArrayList<Item> availableItems) {
		categoryAvailableCrafts = CraftingRecipes.getAvailableCraftsList(availableItems, currentCategoryIndex);
	}

	// Render crafting inventory
	public void render() {
		// Background
		Raylib.drawRectangle(INVENTORY_X_OFFSET, INVENTORY_Y_OFFSET, INVENTORY_WIDTH, INVENTORY_HEIGHT, Inventory.INVENTORY_COLOR);
		
		// Categories
		renderCategories();
		
		// Crafts
		renderCrafts();
	}
	
	public void renderCategories() {
		for (int x = 0; x < CATEGORY_COUNT; x++) {
			// Selected category
			if (x == currentCategoryIndex) {
				int selectedX = INVENTORY_X_OFFSET + INVENTORY_BORDER + x * (CELL_SIZE + INVENTORY_MARGIN) - 4;
				int selectedY = INVENTORY_Y_OFFSET + INVENTORY_BORDER - 4;
				int selectedSize = CELL_SIZE + 8;
				Raylib.drawRectangle(selectedX, selectedY, selectedSize, selectedSize, Inventory.TOOLBAR_SELECTED_COLOR);
			}
			// Cell
			int cellX = INVENTORY_X_OFFSET + INVENTORY_BORDER + x * (CELL_SIZE + INVENTORY_MARGIN);
			int cellY = INVENTORY_Y_OFFSET + INVENTORY_BORDER;

			Raylib.drawRectangle(cellX, cellY, CELL_SIZE, CELL_SIZE, Inventory.INVENTORY_CELL_COLOR);

			int offset = (CELL_SIZE - Block.BLOCK_WIDTH) / 2;

			// Categories
			switch (x) {
				case 0: // All
					RaylibUtils.draw(Assets.grass_item, cellX + offset, cellY + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH);
					break;
				case 1: // Tools
					RaylibUtils.draw(Assets.stone_pickaxe_item, cellX + offset, cellY + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH);
					break;
				case 2: // Blocks
					RaylibUtils.draw(Assets.oak_plank_item, cellX + offset, cellY + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH);
					break;
				case 3: // Miscellaneous
					RaylibUtils.draw(Assets.stick_item, cellX + offset, cellY + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH);
					break;

				default:
					break;
			}
		}
	}
	
	public void renderCrafts() {
		for (int i = 0; i < 9 * 3; i++) {
			int slotX = i % 9;
			int slotY = Math.floorDiv(i, 9);
			
			// Cell
			int cellX = INVENTORY_X_OFFSET + INVENTORY_BORDER + slotX * (CELL_SIZE + INVENTORY_MARGIN);
			int cellY = INVENTORY_Y_OFFSET + INVENTORY_BORDER * 2 + CELL_SIZE + INVENTORY_MARGIN + slotY * (CELL_SIZE + INVENTORY_MARGIN);

			Raylib.drawRectangle(cellX, cellY, CELL_SIZE, CELL_SIZE, Inventory.INVENTORY_CELL_COLOR);
			
			if (categoryAvailableCrafts.size() > i) {
				int craftOffset = (CELL_SIZE - Block.BLOCK_WIDTH) / 2;
				int craftX = cellX + craftOffset;
				int craftY = cellY + craftOffset;
				
				Item craftItem = categoryAvailableCrafts.get(i).craftedItem();
				Inventory.renderItem(craftX, craftY, craftItem, false);
			}
		}
	}
	
	// Check if the mouse is in the crafting inventory
	public boolean isMouseHoveringUI(int mX, int mY) {
        return mX >= INVENTORY_X_OFFSET && mX <= INVENTORY_X_OFFSET + INVENTORY_WIDTH && mY >= INVENTORY_Y_OFFSET && mY <= INVENTORY_Y_OFFSET + INVENTORY_HEIGHT;
    }
	
	// Check if the mouse is in the crafting inventory
	public boolean clickCategory(int mX, int mY) {
		if (!isMouseHoveringUI(mX, mY)) {
			return false;
		}
		
		for (int i = 0; i < CATEGORY_COUNT; i++) {
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
	public CraftingRecipe getMouseHoveringCraft(int mX, int mY) {
		for (int i = 0; i < categoryAvailableCrafts.size(); i++) {
			int slotX = i % 9;
			int slotY = Math.floorDiv(i, 9);
			
			// Cell
			int cellX = INVENTORY_X_OFFSET + INVENTORY_BORDER + slotX * (CELL_SIZE + INVENTORY_MARGIN);
			int cellY = INVENTORY_Y_OFFSET + INVENTORY_BORDER * 2 + CELL_SIZE + INVENTORY_MARGIN + slotY * (CELL_SIZE + INVENTORY_MARGIN);
			
			if (mX >= cellX && mX <= cellX + CELL_SIZE && mY >= cellY && mY <= cellY + CELL_SIZE) {
				return categoryAvailableCrafts.get(i);
			}
		}
		
		return null; // No slot hovered
	}

	/**
	 * Craft as much of the item as possible. Only consume available items.
	 * @param craft {@link CraftingRecipe}
	 * @param inventory {@link Inventory}
	 * @param secondaryContainer {@link ISlotContainer} secondary container where items can be consumed
	 * @param maxAmount Maximum amount to craft. The actual crafted amount can be smaller.
	 * @return The item crafted with the correct count or null if none were crafted.
	 */
	public Item consumeMaxCraftItems(CraftingRecipe craft, Inventory inventory, ISlotContainer secondaryContainer, int maxAmount) {
		int amountPerCraft = craft.craftedItem().getCount();
		if (maxAmount < amountPerCraft) return null;

		int amountCrafted = 0;
		boolean craftSuccessful = consumeCraftItems(craft, inventory, secondaryContainer);
		while (amountCrafted + amountPerCraft <= maxAmount && craftSuccessful) {
			amountCrafted += amountPerCraft;
			craftSuccessful = amountCrafted + amountPerCraft <= maxAmount && consumeCraftItems(craft, inventory, secondaryContainer);
		}

		if (amountCrafted == 0) return null;
		else return new Item(amountCrafted, craft.craftedItem().getType());
	}
	
	// Consume crafting items and return true if everything is working
	public boolean consumeCraftItems(CraftingRecipe craft, ISlotContainer primaryContainer, ISlotContainer secondaryContainer) {
		if (craft == null) {
			System.err.println("No craft provided in CraftingInventory.consumeCraftItems");
			return false;
		} else if (primaryContainer == null) {
			System.err.println("No valid primaryContainer provided in CraftingInventory.consumeCraftItems");
			return false;
		}
		
		ArrayList<Item> itemsCost = craft.itemsCost();
		for (Item itemCost : itemsCost) {
			int itemCostCountRemaining = itemCost.getCount();
			
			int itemCountInventory1 = primaryContainer.countItemId(itemCost.getId());
			int itemCountInventory2 = 0;
			if (secondaryContainer != null) {
				itemCountInventory2 = secondaryContainer.countItemId(itemCost.getId());
			}
			
			if (itemCountInventory1 >= itemCost.getCount()) {
				// Enough items in inventory 1 -> consume items
				if (!primaryContainer.removeItem(itemCost)) {
					System.err.println("Craft went wrong while consuming items in 1 inventory in CraftingInventory.consumeCraftItems");
					return false; // Something went wrong
				}
			} else if (secondaryContainer != null && itemCountInventory1 + itemCountInventory2 >= itemCost.getCount()) {
				// Enough items in both inventories -> consume items from both inventories
				int itemsConsumedInventory1 = Math.min(itemCountInventory1, itemCostCountRemaining);
				int itemsConsumedInventory2 = Math.min(itemCountInventory2, itemCostCountRemaining - itemCountInventory1);
				
				if (!primaryContainer.removeItem(new Item(itemsConsumedInventory1, itemCost.getType())) || !secondaryContainer.removeItem(new Item(itemsConsumedInventory2, itemCost.getType()))) {
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
