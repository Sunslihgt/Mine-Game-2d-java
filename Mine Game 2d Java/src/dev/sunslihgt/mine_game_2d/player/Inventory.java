package dev.sunslihgt.mine_game_2d.player;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.gfx.Text;
import dev.sunslihgt.mine_game_2d.item.Item;

public class Inventory {
	
	// Inventory
	public final static int INVENTORY_SCREEN_CELL_SIZE = 40;
	public final static int INVENTORY_SCREEN_MARGIN = 8, INVENTORY_SCREEN_BORDER = 8;
	
	private int inventoryScreenWidth;
	private int inventoryScreenHeight;
	
	private int inventoryWidth, inventoryHeight, inventoryCellsAmount;
	private int inventoryScreenXOffset, inventoryScreenYOffset;
	
	// Toolbar
	private boolean hasToolbar;
	private int selectedToolbarIndex = 0;
	
	private int toolbarScreenWidth;
	private int toolbarScreenHeight = INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_BORDER * 2;
	
	private int toolbarScreenXOffset, toolbarScreenYOffset;
	
	// Colors
	public final static Color INVENTORY_COLOR = new Color(220, 220, 220);
	public final static Color INVENTORY_CELL_COLOR = new Color(150, 150, 150);
	public final static Color TOOLBAR_SELECTED_COLOR = Color.white;
	
	// Items
	private Item[] items;
	
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
		if (hasToolbar) {
			inventoryCellsAmount += inventoryWidth;
		}
		
		items = new Item[inventoryCellsAmount];
	}
	
	public void render(Graphics g, boolean inventoryOpen) {
		if (hasToolbar) {
			renderToolbar(g);
		}
		if (inventoryOpen) {
			renderInventory(g);
		}
	}
	
	private void renderToolbar(Graphics g) {
		// Background
		g.setColor(INVENTORY_COLOR);
		g.fillRect(toolbarScreenXOffset, toolbarScreenYOffset, toolbarScreenWidth, toolbarScreenHeight);
		
		// Slots
		for (int i = 0; i < inventoryWidth; i++) {
			// Selected slot
			if (i == selectedToolbarIndex) {
				int selectedX = toolbarScreenXOffset + INVENTORY_SCREEN_BORDER + i * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) - 4;
				int selectedY = toolbarScreenYOffset + INVENTORY_SCREEN_BORDER - 4;
				int selectedSize = INVENTORY_SCREEN_CELL_SIZE + 4 * 2;
				g.setColor(TOOLBAR_SELECTED_COLOR);
				g.fillRect(selectedX, selectedY, selectedSize, selectedSize);
			}
			
			// Cell
			int cellX = toolbarScreenXOffset + INVENTORY_SCREEN_BORDER + i * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
			int cellY = toolbarScreenYOffset + INVENTORY_SCREEN_BORDER;
			g.setColor(INVENTORY_CELL_COLOR);
			g.fillRect(cellX, cellY, INVENTORY_SCREEN_CELL_SIZE, INVENTORY_SCREEN_CELL_SIZE);
			
			// Item
			Item item = items[i];
			if (item != null && item.getType().getTexture() != null) {
				int offset = INVENTORY_SCREEN_BORDER + (INVENTORY_SCREEN_CELL_SIZE - Block.BLOCK_WIDTH) / 2;
				int itemX = toolbarScreenXOffset + i * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) + offset;
				int itemY = toolbarScreenYOffset + offset;
				renderItem(g, itemX, itemY, item, false);
			}
		}
	}
	
	private void renderInventory(Graphics g) {
		// Background
		g.setColor(INVENTORY_COLOR);
		g.fillRect(inventoryScreenXOffset, inventoryScreenYOffset, inventoryScreenWidth, inventoryScreenHeight);
		
		// Slots
		if (hasToolbar) {
			for (int i = inventoryWidth; i < inventoryCellsAmount; i++) {
				int slotX = i % inventoryWidth;
				int slotY = Math.floorDiv(i, inventoryWidth) - 1;
				
				// Cell
				int cellX = inventoryScreenXOffset + INVENTORY_SCREEN_BORDER + slotX * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
				int cellY = inventoryScreenYOffset + INVENTORY_SCREEN_BORDER + slotY * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
				g.setColor(INVENTORY_CELL_COLOR);
				g.fillRect(cellX, cellY, INVENTORY_SCREEN_CELL_SIZE, INVENTORY_SCREEN_CELL_SIZE);
				
				// Item
				Item item = items[i];
				if (item != null && item.getType().getTexture() != null) {
					int offset = INVENTORY_SCREEN_BORDER + (INVENTORY_SCREEN_CELL_SIZE - Block.BLOCK_WIDTH) / 2;
					int itemX = inventoryScreenXOffset + slotX * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) + offset;
					int itemY = inventoryScreenYOffset + slotY * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) + offset;
					renderItem(g, itemX, itemY, item, false);
				}
			}
		} else { // No toolbar
			for (int i = 0; i < inventoryCellsAmount; i++) {
				int slotX = i % inventoryWidth;
				int slotY = Math.floorDiv(i, inventoryWidth);
				
				// Cell
				int cellX = inventoryScreenXOffset + INVENTORY_SCREEN_BORDER + slotX * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
				int cellY = inventoryScreenYOffset + INVENTORY_SCREEN_BORDER + slotY * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN);
				g.setColor(INVENTORY_CELL_COLOR);
				g.fillRect(cellX, cellY, INVENTORY_SCREEN_CELL_SIZE, INVENTORY_SCREEN_CELL_SIZE);
				
				// Item
				Item item = items[i];
				if (item != null && item.getType().getTexture() != null) {
					int offset = INVENTORY_SCREEN_BORDER + (INVENTORY_SCREEN_CELL_SIZE - Block.BLOCK_WIDTH) / 2;
					int itemX = inventoryScreenXOffset + slotX * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) + offset;
					int itemY = inventoryScreenYOffset + slotY * (INVENTORY_SCREEN_CELL_SIZE + INVENTORY_SCREEN_MARGIN) + offset;
					renderItem(g, itemX, itemY, item, false);
				}
			}
		}
		
	}
	
	public static void renderItem(Graphics g, int x, int y, Item item, boolean center) {
		if (item == null || item.getType().getTexture() == null) {
			return;
		}
		
		if (center) {
			x -= Block.BLOCK_WIDTH / 2;
			y -= Block.BLOCK_WIDTH / 2;
			g.drawImage(item.getType().getTexture(), x, y, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH, null);
			
			// Item count
			if (item.getType().getMaxStack() != 1) {
				int countX = x + Block.BLOCK_WIDTH + (INVENTORY_SCREEN_CELL_SIZE - Block.BLOCK_WIDTH) / 2;
				int countY = y + Block.BLOCK_WIDTH - 2;
				Text.drawString(g, Integer.toString(item.getCount()), countX, countY, true, true, Color.white, Assets.inventory_font);
			}
		} else {
			g.drawImage(item.getType().getTexture(), x, y, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH, null);
			
			// Item count
			if (item.getType().getMaxStack() != 1) {
				int countX = x + Block.BLOCK_WIDTH + (INVENTORY_SCREEN_CELL_SIZE - Block.BLOCK_WIDTH) / 2;
				int countY = y + Block.BLOCK_WIDTH - 2;
				Text.drawString(g, Integer.toString(item.getCount()), countX, countY, true, true, Color.white, Assets.inventory_font);
			}
		}
	}
	
	// Add items to inventory
	public Item addItem(Item item) {
		// Try to combine with already existing items
		for (int i = 0; i < inventoryCellsAmount; i++) {
			if (items[i] != null && items[i].getId() == item.getId()) {
				int itemsTransfered = Math.min(item.getCount() + items[i].getCount(), item.getType().getMaxStack()) - items[i].getCount();
				items[i].addCount(itemsTransfered);
				
				if (itemsTransfered < item.getCount()) {
					item.addCount(-itemsTransfered);
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
		
		return item; // Items left that should be dropped on the ground
	}
	
	// Add items to inventory
	public ArrayList<Item> addItemList(ArrayList<Item> newItems) {
		for (Iterator<Item> itemsIterator = newItems.iterator(); itemsIterator.hasNext();) {
			Item item = (Item) itemsIterator.next();
			
			boolean transfered = false;
			// Try to combine with already existing items
			for (int i = 0; i < inventoryCellsAmount; i++) {
				if (items[i] != null && items[i].getId() == item.getId()) {
					int itemsTransfered = Math.min(item.getCount() + items[i].getCount(), item.getType().getMaxStack()) - items[i].getCount();
					items[i].addCount(itemsTransfered);
					
					if (itemsTransfered < item.getCount()) {
						item.addCount(-itemsTransfered);
					} else {
						itemsIterator.remove();
						transfered = true;
						break;
					}
				}
			}
			
			if (!transfered) {
				// Try to fill empty spaces
				for (int i = 0; i < inventoryCellsAmount; i++) {
					if (items[i] == null) {
						items[i] = item;
						itemsIterator.remove();
						transfered = true;
						break;
					}
				}
			}
		}
		
		return newItems; // Items left that should be dropped on the ground
	}
	
	// Check if the mouse is in the inventory
	public boolean isMouseInInventory(int mX, int mY, boolean inventoryOpen) {
		// Toolbar
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
	public int getMouseHoveringSlot(int mX, int mY, boolean inventoryOpen) {
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
	
	// Return the item selected in the toolbar
	public Item getToolbarItem() {
		if (hasToolbar) {
			return items[selectedToolbarIndex];
		} else {
			System.err.println("There is no toolbar");
			return null;
		}
	}
	
	// Return whether or not there is a selected item in the toolbar
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
		selectedToolbarIndex += move;
		if (selectedToolbarIndex < 0) {
			selectedToolbarIndex = inventoryWidth + selectedToolbarIndex;
		} else if (selectedToolbarIndex >= inventoryWidth) {
			selectedToolbarIndex = selectedToolbarIndex - inventoryWidth;
		}
	}
	
	public Item getItemWithIndex(int index) {
		if (index < 0 || index >= inventoryCellsAmount) {
			System.err.println("Index out of bounds in Inventory.getItemWithIndex(), index: " + index);
			return null;
		}
		return items[index];
	}
	
	public void setItemWithIndex(int index, Item item) {
		if (index < 0 || index >= inventoryCellsAmount) {
			System.err.println("Index out of bounds in Inventory.setItemWithIndex(), index: " + index);
		}
		items[index] = item;
	}
	
	public int getInventoryCellsAmount() {
		return inventoryCellsAmount;
	}
	
	public Item getSelectedToulbarItem() {
		return items[selectedToolbarIndex];
	}
	
	public void setSelectedToulbarItem(Item item) {
		items[selectedToolbarIndex] = item;
	}
	
	// Return the number of items of a chosen id
	public int countItemId(int id) {
		int count = 0;
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && items[i].getId() == id) {
				count += items[i].getCount();
			}
		}
		return count;
	}
	
	// Remove an item from the inventory and return false if it has been removed
	public boolean removeItem(Item item) {
		if (item.getCount() <= 0) {
			return true;
		}
		int itemCountRemaining = item.getCount();
		
		// Check that there are enough items in inventory
		if (countItemId(item.getId()) < itemCountRemaining) {
			System.err.println("Not enough items in inventory to start removing in Inventory.removeItem()");
			return false;
		}
		
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && items[i].getId() == item.getId()) {
				int itemsRemovedCount = Math.min(itemCountRemaining, items[i].getCount());
				if (items[i].getCount() - itemsRemovedCount <= 0) {
					items[i] = null;
				} else {
					items[i].addCount(-itemsRemovedCount);
				}
				itemCountRemaining -= itemCountRemaining;
				if (itemCountRemaining <= 0) {
					return true;
				}
			}
		}
		
		System.err.println("Not enough items in inventory while removing in Inventory.removeItem()");
		return false;
	}
	
	// Get a list containing all the items in inventory without duplicates
	// WARNING ! Items may have a higher count than max stack count ! 
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
		ArrayList<Item> combinedLists = new ArrayList<Item>(itemsList1);
		
		for (Item item : itemsList2) {
			boolean itemIsInCombinedList = false;
			for (Item itemInCombinedList : combinedLists) {
				if (itemInCombinedList.getId() == item.getId()) {
					item.addCount(itemInCombinedList.getCount());
					itemIsInCombinedList = true;
					break;
				}
			}
			if (!itemIsInCombinedList) {
				combinedLists.add(item);
			}
		}
		
		return combinedLists;
	}
	
}
