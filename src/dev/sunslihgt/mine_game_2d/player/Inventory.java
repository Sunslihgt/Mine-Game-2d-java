package dev.sunslihgt.mine_game_2d.player;

import java.util.ArrayList;
import java.util.Iterator;

import com.raylib.Raylib;
import com.raylib.Color;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.gfx.font.Text;
import dev.sunslihgt.mine_game_2d.gfx.font.Text.FontAnchor;
import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.utils.RaylibUtils;

public class Inventory implements ISlotContainer {
	
	// Inventory
	public final static int INVENTORY_SCREEN_CELL_SIZE = 40;
	public final static int INVENTORY_SCREEN_MARGIN = 8, INVENTORY_SCREEN_BORDER = 8;
	
	private final int inventoryScreenWidth;
	private final int inventoryScreenHeight;
	
	private final int inventoryWidth, inventoryHeight;
	private int inventoryCellsAmount;
	private final int inventoryScreenXOffset, inventoryScreenYOffset;
	
	// Toolbar
	private final boolean hasToolbar;
	private int selectedToolbarIndex = 0;
	
	private final int toolbarScreenWidth;
	private final int toolbarScreenHeight = INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_BORDER * 2;
	
	private final int toolbarScreenXOffset, toolbarScreenYOffset;
	
	// Colors
	public final static Color INVENTORY_COLOR = RaylibUtils.createColor(220, 220, 220);
	public final static Color INVENTORY_CELL_COLOR = RaylibUtils.createColor(150, 150, 150);
	public final static Color TOOLBAR_SELECTED_COLOR = Raylib.WHITE;
	
	// Items
	private final Item[] items;
	
	public Inventory(int inventoryWidth, int inventoryHeight, boolean hasToolbar, int inventoryScreenXOffset, int inventoryScreenYOffset, int toolbarScreenYOffset) {
		
		// Inventory rows and columns
		this.inventoryWidth = inventoryWidth;
		this.inventoryHeight = inventoryHeight;
		
		// Screen parameters
		inventoryScreenWidth = this.inventoryWidth * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) - INVENTORY_SCREEN_MARGIN + INVENTORY_SCREEN_BORDER * 2;
		inventoryScreenHeight = this.inventoryHeight * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) - INVENTORY_SCREEN_MARGIN + INVENTORY_SCREEN_BORDER * 2;
		toolbarScreenWidth = inventoryScreenWidth;
		
		this.inventoryScreenXOffset = inventoryScreenXOffset;
		this.inventoryScreenYOffset = inventoryScreenYOffset;
		this.toolbarScreenXOffset = inventoryScreenXOffset;
		this.toolbarScreenYOffset = toolbarScreenYOffset;
		
		// Toolbar and Items
		this.hasToolbar = hasToolbar;
		inventoryCellsAmount = inventoryWidth * inventoryHeight;
		if (hasToolbar) { // Add a row
			inventoryCellsAmount += inventoryWidth;
		}
		
		items = new Item[inventoryCellsAmount];
	}
	
	public void render(boolean inventoryOpen) {
		if (hasToolbar) {
			renderToolbar();
		}
		if (inventoryOpen) {
			renderInventory();
		}
	}
	
	private void renderToolbar() {
		// Background
		Raylib.drawRectangle(toolbarScreenXOffset, toolbarScreenYOffset, toolbarScreenWidth, toolbarScreenHeight, INVENTORY_COLOR);
		
		// Slots
		for (int i = 0; i < inventoryWidth; i++) {
			// Selected slot
			if (i == selectedToolbarIndex) {
				int selectedX = toolbarScreenXOffset + INVENTORY_SCREEN_BORDER + i * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) - 4;
				int selectedY = toolbarScreenYOffset + INVENTORY_SCREEN_BORDER - 4;
				int selectedSize = INVENTORY_SCREEN_CELL_SIZE + 4 * 2;
				Raylib.drawRectangle(selectedX, selectedY, selectedSize, selectedSize, TOOLBAR_SELECTED_COLOR);
			}
			
			// Cell
			int cellX = toolbarScreenXOffset + INVENTORY_SCREEN_BORDER + i * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
			int cellY = toolbarScreenYOffset + INVENTORY_SCREEN_BORDER;
			Raylib.drawRectangle(cellX, cellY, INVENTORY_SCREEN_CELL_SIZE, INVENTORY_SCREEN_CELL_SIZE, INVENTORY_CELL_COLOR);
			
			// Item
			Item item = items[i];
			if (item != null && item.getType().getTexture() != null) {
				int offset = INVENTORY_SCREEN_BORDER + (INVENTORY_SCREEN_CELL_SIZE - Block.BLOCK_WIDTH) / 2;
				int itemX = toolbarScreenXOffset + i * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) + offset;
				int itemY = toolbarScreenYOffset + offset;
				renderItem(itemX, itemY, item, false);
			}
		}
	}
	
	private void renderInventory() {
		// Background
		Raylib.drawRectangle(inventoryScreenXOffset, inventoryScreenYOffset, inventoryScreenWidth, inventoryScreenHeight, INVENTORY_COLOR);
		
		// Slots
		if (hasToolbar) {
			for (int i = inventoryWidth; i < inventoryCellsAmount; i++) {
				int slotX = i % inventoryWidth;
				int slotY = Math.floorDiv(i, inventoryWidth) - 1;
				
				// Cell
				int cellX = inventoryScreenXOffset + INVENTORY_SCREEN_BORDER + slotX * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
				int cellY = inventoryScreenYOffset + INVENTORY_SCREEN_BORDER + slotY * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
				Raylib.drawRectangle(cellX, cellY, INVENTORY_SCREEN_CELL_SIZE, INVENTORY_SCREEN_CELL_SIZE, INVENTORY_CELL_COLOR);
				
				// Item
				Item item = items[i];
				if (item != null && item.getType().getTexture() != null) {
					int offset = INVENTORY_SCREEN_BORDER + (INVENTORY_SCREEN_CELL_SIZE - Block.BLOCK_WIDTH) / 2;
					int itemX = inventoryScreenXOffset + slotX * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) + offset;
					int itemY = inventoryScreenYOffset + slotY * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) + offset;
					renderItem(itemX, itemY, item, false);
				}
			}
		} else { // No toolbar
			for (int i = 0; i < inventoryCellsAmount; i++) {
				int slotX = i % inventoryWidth;
				int slotY = Math.floorDiv(i, inventoryWidth);
				
				// Cell
				int cellX = inventoryScreenXOffset + INVENTORY_SCREEN_BORDER + slotX * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
				int cellY = inventoryScreenYOffset + INVENTORY_SCREEN_BORDER + slotY * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
				Raylib.drawRectangle(cellX, cellY, INVENTORY_SCREEN_CELL_SIZE, INVENTORY_SCREEN_CELL_SIZE, INVENTORY_CELL_COLOR);
				
				// Item
				Item item = items[i];
				if (item != null && item.getType().getTexture() != null) {
					int offset = INVENTORY_SCREEN_BORDER + (INVENTORY_SCREEN_CELL_SIZE - Block.BLOCK_WIDTH) / 2;
					int itemX = inventoryScreenXOffset + slotX * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) + offset;
					int itemY = inventoryScreenYOffset + slotY * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) + offset;
					renderItem(itemX, itemY, item, false);
				}
			}
		}
		
	}
	
	public static void renderItem(int x, int y, Item item, boolean center) {
		if (item == null || item.getType().getTexture() == null) {
			return;
		}
		
		if (center) {
			x -= Block.BLOCK_WIDTH / 2;
			y -= Block.BLOCK_WIDTH / 2;
		}

		RaylibUtils.draw(item.getType().getTexture(), x, y, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH);

		// Item count
		if (item.getType().getMaxStack() != 1) {
			int countX = x + Block.BLOCK_WIDTH + 3;
			int countY = y + Block.BLOCK_WIDTH + 6;
			Text.drawString(Integer.toString(item.getCount()), countX, countY, Assets.inventory_font, FontAnchor.BOTTOM_RIGHT, Raylib.WHITE);
		}
	}

	/**
	 * Add items to inventory. The remaining items
	 */
	@Override
	public Item addItem(Item item) {
		// Try to combine with already existing items
		for (int i = 0; i < inventoryCellsAmount; i++) {
			if (items[i] != null && items[i].getId() == item.getId()) {
				int itemsTransferred = Math.min(item.getCount() + items[i].getCount(), item.getType().getMaxStack()) - items[i].getCount();
				items[i].addCount(itemsTransferred);
				
				if (itemsTransferred < item.getCount()) {
					item.addCount(-itemsTransferred);
				} else {
					return null;
				}
			}
		}
		
		// Try to fill empty spaces
		for (int i = 0; i < inventoryCellsAmount; i++) {
			if (items[i] == null) {
				items[i] = item;
				return null;
			}
		}
		
		return item; // Items remaining
	}


	/**
	 * Add item list to inventory. Returns a list with the remaining items
	 */
	@Override
	public ArrayList<Item> addItemList(ArrayList<Item> newItems) {
		for (Iterator<Item> itemsIterator = newItems.iterator(); itemsIterator.hasNext();) {
			Item item = itemsIterator.next();

			Item remainingItem = addItem(item);
			if (remainingItem == null || remainingItem.getCount() == 0) {
				itemsIterator.remove();
			}
		}

		return newItems; // Items remaining
	}
	
	// Check if the mouse is in the inventory
	@Override
	public boolean isMouseHoveringUI(int mX, int mY, boolean inventoryOpen) {
		// Toolbar (if it exists)
		if (hasToolbar) {
			if (mX >= toolbarScreenXOffset && mX <= toolbarScreenXOffset + toolbarScreenWidth && mY >= toolbarScreenYOffset && mY <= toolbarScreenYOffset + toolbarScreenHeight) {
				return true;
			}
		}
		
		// Inventory
		if (inventoryOpen) {
			if (mX >= inventoryScreenXOffset && mX <= inventoryScreenXOffset + inventoryScreenWidth && mY >= inventoryScreenYOffset && mY <= inventoryScreenYOffset + inventoryScreenHeight) {
				return true;
			}
		}
		
		return false;
	}
	
	// Get the slot hovered by the mouse (if none return -1)
	@Override
	public int getMouseSlotIndex(int mX, int mY, boolean inventoryOpen) {
		for (int i = 0; i < inventoryCellsAmount; i++) {
			int slotX = i % inventoryWidth;
			int slotY = Math.floorDiv(i, inventoryWidth) - 1;
			
			if (!hasToolbar) {
				slotY = Math.floorDiv(i, inventoryWidth);
			}
			
			int slotStartX = inventoryScreenXOffset + INVENTORY_SCREEN_BORDER + slotX * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
			int slotStartY = inventoryScreenYOffset + INVENTORY_SCREEN_BORDER + slotY * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
			if (hasToolbar && i < inventoryWidth) { // Toolbar
				slotStartY = toolbarScreenYOffset + INVENTORY_SCREEN_BORDER;
			}
			
			if (mX >= slotStartX && mX <= slotStartX + INVENTORY_SCREEN_CELL_SIZE && mY >= slotStartY && mY <= slotStartY + INVENTORY_SCREEN_CELL_SIZE) {
				if (i >= inventoryWidth && !inventoryOpen) {
					return -1; // Return -1 if the inventory was closed and not in toolbar
				}
				return i;
			}
		}
		
		return -1; // No slot hovered
	}

	@Override
	public Item getMouseSlotItem(int mX, int mY, boolean inventoryOpen) {
		int slot = getMouseSlotIndex(mX, mY, inventoryOpen);
		if (slot >= 0 && slot < getInventoryCellsAmount()) return getItemWithIndex(slot);
		return null;
	}

	/**
	 * Transfers an item from the toolbar to the rest of the inventory or the other way around.
	 * @param srcSlot The slot of the item to be moved
	 */
	public void transferSlotToolbarInventory(int srcSlot) {
		Item srcItem = getItemWithIndex(srcSlot);
		if (!hasToolbar || inventoryCellsAmount <= inventoryWidth) return;
		if (srcItem == null) return;

		int destinationSlotMin = srcSlot < inventoryWidth ? inventoryWidth       : 0;
		int destinationSlotMax = srcSlot < inventoryWidth ? inventoryCellsAmount : inventoryWidth;

		// Fill slots with the same item
		for (int dstSlot = destinationSlotMin; dstSlot < destinationSlotMax; dstSlot++) {
			Item dstItem = getItemWithIndex(dstSlot);
			if (dstItem != null && dstItem.getId() == srcItem.getId()) {
				int amountTransferred = Math.min(srcItem.getCount(), dstItem.getType().getMaxStack() - dstItem.getCount());
				dstItem.addCount(amountTransferred);
				if (amountTransferred < srcItem.getCount()) {
					srcItem.addCount(-amountTransferred);
				} else {
					setItemWithIndex(srcSlot, null);
					return;
				}
			}
		}

		// Fill the first empty slots
		for (int dstSlot = destinationSlotMin; dstSlot < destinationSlotMax; dstSlot++) {
			Item dstItem = getItemWithIndex(dstSlot);
			if (dstItem == null) {
				items[dstSlot] = srcItem;
				setItemWithIndex(srcSlot, null);
				return;
			}
		}
	}

	// Return the item selected in the toolbar
	public Item getToolbarItem() {
		if (hasToolbar) {
			return items[selectedToolbarIndex];
		} else {
			System.err.println("There is no toolbar");
			return null;
		}
	}
	
	// Return whether there is a selected item in the toolbar
	public boolean hasSelectedToolbarItem() {
		if (hasToolbar) {
			return (items[selectedToolbarIndex] != null);
		} else {
			System.err.println("There is no toolbar");
			return false;
		}
	}

	// Move selected toolbar index (when using mouse wheel)
	public void moveToolbarIndex(int move) {
		selectedToolbarIndex -= move;
		if (selectedToolbarIndex < 0) {
			selectedToolbarIndex = inventoryWidth + selectedToolbarIndex;
		} else if (selectedToolbarIndex >= inventoryWidth) {
			selectedToolbarIndex = selectedToolbarIndex - inventoryWidth;
		}
	}

	@Override
	public Item getItemWithIndex(int index) {
		if (index < 0 || index >= inventoryCellsAmount) {
			System.err.println("Index out of bounds in Inventory.getItemWithIndex(), index: " + index);
			return null;
		}
		return items[index];
	}

	@Override
	public void setItemWithIndex(int index, Item item) {
		if (index < 0 || index >= inventoryCellsAmount) {
			System.err.println("Index out of bounds in Inventory.setItemWithIndex(), index: " + index);
		}
		items[index] = item;
	}

	@Override
	public void setItemCountWithIndex(int index, int count) {
		if (index < 0 || index >= inventoryCellsAmount) {
			System.err.println("Index out of bounds in Inventory.setItemWithIndex(), index: " + index);
		}
		items[index].setCount(count);
	}

	@Override
	public int getInventoryCellsAmount() {
		return inventoryCellsAmount;
	}
	
	public Item getSelectedToolbarItem() {
		if (!hasToolbar) {
			System.err.println("Inventory.getSelectedToolbarItem: No toolbar for this inventory");
			return null;
		}
		return items[selectedToolbarIndex];
	}
	
	public void setSelectedToolbarItem(Item item) {
		if (!hasToolbar) {
			System.err.println("Inventory.getSelectedToolbarItem: No toolbar for this inventory");
		} else {
			items[selectedToolbarIndex] = item;
		}
	}
	
	// Return the number of items of a chosen id
	@Override
	public int countItemId(int id) {
		int count = 0;
        for (Item item : items) {
            if (item != null && item.getId() == id) {
                count += item.getCount();
            }
        }
		return count;
	}

	/**
	 * Attempt to remove an item from the inventory. Do not remove items if the count is higher than the items in the inventory.
	 * @param item Item with count to remove from the inventory
	 * @return true if the item was removed, false if not possible.
	 */
	@Override
	public boolean removeItem(Item item) {
		if (item.getCount() <= 0) {
			return true;
		}
		int itemsToRemove = item.getCount();
		
		// Check that there are enough items in inventory
		if (countItemId(item.getId()) < itemsToRemove) {
			System.err.println("Not enough items in inventory to start removing in Inventory.removeItem()");
			return false;
		}
		
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && items[i].getId() == item.getId()) {
				int itemsRemovedCount = Math.min(itemsToRemove, items[i].getCount());
				if (items[i].getCount() - itemsRemovedCount <= 0) {
					items[i] = null;
				} else {
					items[i].addCount(-itemsRemovedCount);
				}
				itemsToRemove -= itemsRemovedCount;
				if (itemsToRemove <= 0) {
					return true;
				}
			}
		}
		
		System.err.println("Not enough items in inventory while removing in Inventory.removeItem()");
		return false;
	}

	@Override
	public void clearInventory() {
		for (int i = 0; i < inventoryCellsAmount; i++) {
			items[i] = null;
		}
	}

	/**
	 * Get a list containing all the items in inventory without duplicates.<br>
	 * WARNING ! Items may have a higher count than max stack count !
	 */
	@Override
	public ArrayList<Item> getItemsListCopy() {
		ArrayList<Item> itemsList = new ArrayList<Item>();
		
		for (Item item : items) {
			if (item != null) {
				// Try to add item to an existing item in the list
				boolean itemIsInList = false;
				for (Item itemInList : itemsList) {
					if (item.getId() == itemInList.getId()) {
						itemInList.addCount(item.getCount());
						itemIsInList = true;
						break;
					}
				}
				// If the item is not in the list yet, add it
				if (!itemIsInList) {
					itemsList.add(item.getCopy());
				}
			}
		}
		
		return itemsList;
	}
	
	// Warning ! Will modify lists
	public static ArrayList<Item> combineItemsLists(ArrayList<Item> itemsList1, ArrayList<Item> itemsList2) {
		if (itemsList1 == null && itemsList2 == null) return new ArrayList<>();
		if (itemsList1 == null) return itemsList2;
		if (itemsList2 == null) return itemsList1;

		ArrayList<Item> combinedLists = new ArrayList<>(itemsList1);
		
		for (Item newItem : itemsList2) {
			boolean itemIsInCombinedList = false;
			for (Item itemInCombinedList : combinedLists) {
				if (itemInCombinedList.getId() == newItem.getId()) {
					itemInCombinedList.addCount(newItem.getCount());
					itemIsInCombinedList = true;
					break;
				}
			}
			if (!itemIsInCombinedList) {
				combinedLists.add(newItem);
			}
		}
		
		return combinedLists;
	}

	public boolean getHasToolbar() {
		return hasToolbar;
	}
}
