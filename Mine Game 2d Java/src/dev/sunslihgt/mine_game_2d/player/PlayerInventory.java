package dev.sunslihgt.mine_game_2d.player;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.block.tile_entity.ChestTileEntity;
import dev.sunslihgt.mine_game_2d.block.tile_entity.FurnaceTileEntity;
import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.recipes.CraftingRecipe;

public class PlayerInventory {
	
	public final static int INVENTORY_X_OFFSET = 40, INVENTORY_Y_OFFSET = 100;
	private final static int TOOLBAR_Y_OFFSET = 30;
	
	private Inventory inventory;
	private CraftingInventory craftingInventory;
	
	private boolean inventoryOpen = false;
	
	private Item cursorSelectedItem;
	
	// Enum of possible opened inventories
	public static enum OpenedInventoryEnum {
		CHEST, FURNACE, NONE
	}
	
	private OpenedInventoryEnum selectedInventory = OpenedInventoryEnum.NONE;
	private ChestTileEntity chestSelected;
	private FurnaceTileEntity furnaceSelected;
	
	private boolean lastLeftClick = false, lastRightClick = false;
	
	private Handler handler;
	
	public PlayerInventory(Handler handler) {
		this.handler = handler;
		
		init();
	}
	
	private void init() {
		inventory = new Inventory(9, 3, true, INVENTORY_X_OFFSET, INVENTORY_Y_OFFSET, TOOLBAR_Y_OFFSET);
		inventory.setItemWithIndex(0, new Item(1, ItemType.woodenPickaxeItem));
		inventory.setItemWithIndex(1, new Item(1, ItemType.stonePickaxeItem));
		inventory.setItemWithIndex(2, new Item(1, ItemType.ironPickaxeItem));
		inventory.setItemWithIndex(3, new Item(12, ItemType.stoneItem));
		inventory.setItemWithIndex(4, new Item(48, ItemType.dirtItem));
		inventory.setItemWithIndex(5, new Item(64, ItemType.coalItem));
		inventory.setItemWithIndex(6, new Item(16, ItemType.torchItem));
		inventory.setItemWithIndex(7, new Item(3, ItemType.chestItem));
		inventory.setItemWithIndex(8, new Item(7, ItemType.furnaceItem));
		
		inventory.setItemWithIndex(9, new Item(1, ItemType.woodenAxeItem));
		inventory.setItemWithIndex(10, new Item(1, ItemType.stoneAxeItem));
		inventory.setItemWithIndex(11, new Item(1, ItemType.ironAxeItem));

		inventory.setItemWithIndex(18, new Item(1, ItemType.woodenShovelItem));
		inventory.setItemWithIndex(19, new Item(1, ItemType.stoneShovelItem));
		inventory.setItemWithIndex(20, new Item(1, ItemType.ironShovelItem));
		
		inventory.setItemWithIndex(13, new Item(64, ItemType.coalOreItem));
		inventory.setItemWithIndex(14, new Item(64, ItemType.ironOreItem));
		inventory.setItemWithIndex(15, new Item(64, ItemType.rubyOreItem));
		inventory.setItemWithIndex(16, new Item(64, ItemType.stickItem));
		
		inventory.setItemWithIndex(23, new Item(64, ItemType.oakWoodItem));
		inventory.setItemWithIndex(24, new Item(64, ItemType.oakPlankItem));
		inventory.setItemWithIndex(25, new Item(64, ItemType.oakLeavesItem));
		
		craftingInventory = new CraftingInventory();
	}
	
	public void tick() {
		checkMouseInput();
		
//		System.out.println("Torch craft possible: " + CraftingRecipes.isCraftAffordable(CraftingRecipes.craftingRecipes.get(0), inventory.getItemsListCopy()));
	}
	
	// Manage mouse inputs in inventory (and toolbar)
	public void checkMouseInput() {
		boolean leftClicked = handler.getMouseManager().isLeftPressed();
		boolean rightClicked = handler.getMouseManager().isRightPressed();
		
		int mX = handler.getMouseManager().getMouseX();
		int mY = handler.getMouseManager().getMouseY();
		
		// Mouse Wheel
		inventory.moveToolbarIndex(handler.getMouseManager().getScroll());
		
		// Inventory
		if (inventoryOpen) {
			// Inventory
			if (inventory.isMouseInInventory(mX, mY, true)) {
				int inventorySlot = inventory.getMouseHoveringSlot(mX, mY, true);
				if (inventorySlot >= 0 && inventorySlot < inventory.getInventoryCellsAmount()) { // Mouse hovering slot
					Item inventorySlotItem = inventory.getItemWithIndex(inventorySlot);
					if (leftClicked && !lastLeftClick) { // Left click
						if (cursorSelectedItem == null) { // No selected item -> select new
							if (inventorySlotItem != null) {
								cursorSelectedItem = inventorySlotItem;
								inventory.setItemWithIndex(inventorySlot, null);
							}
						} else { // Mouse clicks a second slot
							// Move item
							if (inventorySlotItem == null) {
								inventory.setItemWithIndex(inventorySlot, cursorSelectedItem);
								cursorSelectedItem = null;
							} else if (cursorSelectedItem.getId() == inventorySlotItem.getId()) { // Combine items
								int inventoryItemCount = Math.min(inventorySlotItem.getCount() + cursorSelectedItem.getCount(), cursorSelectedItem.getType().getMaxStack());
								int cursorItemCount = inventorySlotItem.getCount() + cursorSelectedItem.getCount() - inventoryItemCount;
								inventorySlotItem.setCount(inventoryItemCount);
								if (cursorItemCount <= 0) { // No more cursor item -> reset
									cursorSelectedItem = null;
								} else {
									cursorSelectedItem.setCount(cursorItemCount);
								}
							} else { // Different items -> Swap items
								Item cursorItemBuffer = cursorSelectedItem;
								cursorSelectedItem = inventorySlotItem;
								inventory.setItemWithIndex(inventorySlot, cursorItemBuffer);
							}
						}
					} else if (rightClicked && !lastRightClick) { // Right click
						if (cursorSelectedItem == null) { // No selected item -> select half of item stack
							if (inventorySlotItem != null) {
								int inventoryItemCount = inventorySlotItem.getCount() / 2;
								int cursorItemCount = inventorySlotItem.getCount() - inventoryItemCount;
								cursorSelectedItem = new Item(cursorItemCount, inventorySlotItem.getType());
								if (inventoryItemCount <= 0) {
									inventory.setItemWithIndex(inventorySlot, null);
								} else {
									inventorySlotItem.setCount(inventoryItemCount);
								}
							}
						} else { // Cursor selected item -> select a 2nd slot
							if (inventorySlotItem == null) { // Deposit one item in empty slot
								inventory.setItemWithIndex(inventorySlot, new Item(1, cursorSelectedItem.getType()));
								if (cursorSelectedItem.getCount() - 1 <= 0) {
									cursorSelectedItem = null;
								} else {
									cursorSelectedItem.addCount(-1);
								}
							} else if (inventorySlotItem.getId() == cursorSelectedItem.getId()) { // Deposit one item in slot
								if (inventorySlotItem.getCount() + 1 <= inventorySlotItem.getType().getMaxStack()) { // slot not full
									inventorySlotItem.addCount(1);
									if (cursorSelectedItem.getCount() - 1 <= 0) {
										cursorSelectedItem = null;
									} else {
										cursorSelectedItem.addCount(-1);
									}
								}
							} else { // Different items -> Swap items
								Item cursorItemBuffer = cursorSelectedItem;
								cursorSelectedItem = inventorySlotItem;
								inventory.setItemWithIndex(inventorySlot, cursorItemBuffer);
							}
						}
					}
				}
			}
			// Crafting inventory
			else if (craftingInventory.isMouseInInventory(mX, mY)) {
				if (((leftClicked && !lastLeftClick) || (leftClicked && !lastLeftClick)) && !craftingInventory.clickCategory(mX, mY)) {
					ArrayList<Item> availableItems = inventory.getItemsListCopy();
					if (selectedInventory == OpenedInventoryEnum.CHEST && chestSelected != null) {
						availableItems = Inventory.combineItemsLists(availableItems, chestSelected.getChestInventory().getItemsListCopy());
					}
					CraftingRecipe craft = craftingInventory.getMouseHoveringCraft(mX, mY, availableItems);
					
					if (craft != null) {
						Item craftedItem = craft.getCraftedItem().getCopy();
						if (cursorSelectedItem == null || (cursorSelectedItem.getId() == craftedItem.getId() && cursorSelectedItem.getCount() + craftedItem.getCount() <= craftedItem.getType().getMaxStack())) {
							if (selectedInventory == OpenedInventoryEnum.CHEST && chestSelected != null) {
								if (craftingInventory.consumeCraftItems(craft, inventory, chestSelected.getChestInventory())) {
									if (cursorSelectedItem == null) {
										cursorSelectedItem = craftedItem;
									} else {
										cursorSelectedItem.addCount(craftedItem.getCount());
									}
								}
							} else {
								if (craftingInventory.consumeCraftItems(craft, inventory, null)) {
									if (cursorSelectedItem == null) {
										cursorSelectedItem = craftedItem;
									} else {
										cursorSelectedItem.addCount(craftedItem.getCount());
									}
								}
							}
						}
					}
				}
			}
			// Chest inventory
			else if (selectedInventory == OpenedInventoryEnum.CHEST && chestSelected != null && chestSelected.getChestInventory().isMouseInInventory(mX, mY, true)) { // Mouse not on inventory slot and chest open
				Inventory chestInventory = chestSelected.getChestInventory();
				int chestSlot = chestInventory.getMouseHoveringSlot(mX, mY, true);
				
				// Mouse on chest cell
				if (chestSlot >= 0 && chestSlot < chestInventory.getInventoryCellsAmount()) {
					Item chestItem = chestInventory.getItemWithIndex(chestSlot);
					if (leftClicked && !lastLeftClick) { // Left click
						if (cursorSelectedItem == null) { // No selected item -> select new
							if (chestItem != null) {
								cursorSelectedItem = chestItem;
								chestInventory.setItemWithIndex(chestSlot, null);
							}
						} else { // Mouse clicks a second slot
							// Move item
							if (chestItem == null) {
								chestInventory.setItemWithIndex(chestSlot, cursorSelectedItem);
								cursorSelectedItem = null;
							} else if (cursorSelectedItem.getId() == chestItem.getId()) { // Combine items
								int chestItemCount = Math.min(chestItem.getCount() + cursorSelectedItem.getCount(), cursorSelectedItem.getType().getMaxStack());
								int cursorItemCount = chestItem.getCount() + cursorSelectedItem.getCount() - chestItemCount;
								chestItem.setCount(chestItemCount);
								if (cursorItemCount <= 0) { // No more cursor item -> reset
									cursorSelectedItem = null;
								} else {
									cursorSelectedItem.setCount(cursorItemCount);
								}
							} else { // Different items -> Swap items
								Item cursorItemBuffer = cursorSelectedItem;
								cursorSelectedItem = chestItem;
								chestInventory.setItemWithIndex(chestSlot, cursorItemBuffer);
							}
						}
					} else if (rightClicked && !lastRightClick) { // Right click
						if (cursorSelectedItem == null) { // No selected item -> select half of item stack
							if (chestItem != null) {
								int chestItemCount = chestItem.getCount() / 2;
								int cursorItemCount = chestItem.getCount() - chestItemCount;
								cursorSelectedItem = new Item(cursorItemCount, chestItem.getType());
								if (chestItemCount <= 0) {
									chestInventory.setItemWithIndex(chestSlot, null);
								} else {
									chestItem.setCount(chestItemCount);
								}
							}
						} else { // Cursor selected item -> select a 2nd slot
							if (chestItem == null) { // Deposit one item in empty slot
								chestInventory.setItemWithIndex(chestSlot, new Item(1, cursorSelectedItem.getType()));
								if (cursorSelectedItem.getCount() - 1 <= 0) {
									cursorSelectedItem = null;
								} else {
									cursorSelectedItem.addCount(-1);
								}
							} else if (chestItem.getId() == cursorSelectedItem.getId()) { // Deposit one item in slot
								if (chestItem.getCount() + 1 <= chestItem.getType().getMaxStack()) { // slot not full
									chestItem.addCount(1);
									if (cursorSelectedItem.getCount() - 1 <= 0) {
										cursorSelectedItem = null;
									} else {
										cursorSelectedItem.addCount(-1);
									}
								}
							} else { // Different items -> Swap items
								Item cursorItemBuffer = cursorSelectedItem;
								cursorSelectedItem = chestItem;
								chestInventory.setItemWithIndex(chestSlot, cursorItemBuffer);
							}
						}
					}
				}
			}
			// Furnace Inventory
			else if (selectedInventory == OpenedInventoryEnum.FURNACE && furnaceSelected != null && furnaceSelected.isMouseInInventory(mX, mY)) { // Mouse not on inventory slot and furnace open
				int furnaceSlot = furnaceSelected.getMouseHoveringSlot(mX, mY, true);
				// Mouse on furnace cell
				if (furnaceSlot >= 0 && furnaceSlot < 3) {
					Item furnaceItem = furnaceSelected.getItemWithIndex(furnaceSlot);
					if (leftClicked && !lastLeftClick) { // Left click
						if (cursorSelectedItem == null) { // No selected item -> select new
							if (furnaceItem != null) {
								cursorSelectedItem = furnaceItem;
								furnaceSelected.setItemWithIndex(furnaceSlot, null);
							}
						} else { // Mouse clicks a second slot
							// Move item
							if (furnaceItem == null) {
								furnaceSelected.setItemWithIndex(furnaceSlot, cursorSelectedItem);
								cursorSelectedItem = null;
							} else if (cursorSelectedItem.getId() == furnaceItem.getId()) { // Combine items
								int furnaceItemCount = Math.min(furnaceItem.getCount() + cursorSelectedItem.getCount(), cursorSelectedItem.getType().getMaxStack());
								int cursorItemCount = furnaceItem.getCount() + cursorSelectedItem.getCount() - furnaceItemCount;
								furnaceItem.setCount(furnaceItemCount);
								if (cursorItemCount <= 0) { // No more cursor item -> reset
									cursorSelectedItem = null;
								} else {
									cursorSelectedItem.setCount(cursorItemCount);
								}
							} else { // Different items -> Swap items
								Item cursorItemBuffer = cursorSelectedItem;
								cursorSelectedItem = furnaceItem;
								furnaceSelected.setItemWithIndex(furnaceSlot, cursorItemBuffer);
							}
						}
					} else if (rightClicked && !lastRightClick) { // Right click
						if (cursorSelectedItem == null) { // No selected item -> select half of item stack
							if (furnaceItem != null) {
								int furnaceItemCount = furnaceItem.getCount() / 2;
								int cursorItemCount = furnaceItem.getCount() - furnaceItemCount;
								cursorSelectedItem = new Item(cursorItemCount, furnaceItem.getType());
								if (furnaceItemCount <= 0) {
									furnaceSelected.setItemWithIndex(furnaceSlot, null);
								} else {
									furnaceItem.setCount(furnaceItemCount);
								}
							}
						} else { // Cursor selected item -> select a 2nd slot
							if (furnaceItem == null) { // Deposit one item in empty slot
								furnaceSelected.setItemWithIndex(furnaceSlot, new Item(1, cursorSelectedItem.getType()));
								if (cursorSelectedItem.getCount() - 1 <= 0) {
									cursorSelectedItem = null;
								} else {
									cursorSelectedItem.addCount(-1);
								}
							} else if (furnaceItem.getId() == cursorSelectedItem.getId()) { // Deposit one item in slot
								if (furnaceItem.getCount() + 1 <= furnaceItem.getType().getMaxStack()) { // slot not full
									furnaceItem.addCount(1);
									if (cursorSelectedItem.getCount() - 1 <= 0) {
										cursorSelectedItem = null;
									} else {
										cursorSelectedItem.addCount(-1);
									}
								}
							} else { // Different items -> Swap items
								Item cursorItemBuffer = cursorSelectedItem;
								cursorSelectedItem = furnaceItem;
								furnaceSelected.setItemWithIndex(furnaceSlot, cursorItemBuffer);
							}
						}
					}
				}
			}
		} else { // Inventory closed
			if (cursorSelectedItem != null) { // Item selected
				// Drop or transfer item
				
				// Add items to inventory and store remaining items
				@SuppressWarnings("unused")
				Item itemsLeft = inventory.addItem(cursorSelectedItem);
				cursorSelectedItem = null;
				
				// TODO: itemsLeft should be dropped
			}
		}
		
		lastLeftClick = leftClicked;
		lastRightClick = rightClicked;
	}
	
	public void render(Graphics g) {
		// Render inventory and toolbar
		inventory.render(g, inventoryOpen);
		
		// Render crafting inventory
		if (inventoryOpen) {
			ArrayList<Item> availableItems = inventory.getItemsListCopy();
			
			if (selectedInventory == OpenedInventoryEnum.CHEST && chestSelected != null) {
				availableItems = Inventory.combineItemsLists(availableItems, chestSelected.getChestInventory().getItemsListCopy());
			}
			
			craftingInventory.render(g, availableItems);
		}
		
		// Render open chest
		if (selectedInventory == OpenedInventoryEnum.CHEST && chestSelected != null) {
			chestSelected.renderChestInventory(g);
		} else if (selectedInventory == OpenedInventoryEnum.FURNACE && furnaceSelected != null) {
			furnaceSelected.renderFurnaceInventory(g);
		}
		
		// Render cursor item
		if (cursorSelectedItem != null) {
			int mouseX = handler.getMouseManager().getMouseX();
			int mouseY = handler.getMouseManager().getMouseY();
			Inventory.renderItem(g, mouseX, mouseY, cursorSelectedItem, true);
		}
	}
	
	public void selectedItemLeftClick() {
		if (handler.getPlayer().getPlayerCursor().isCursorVisible()) {
			// Damage block
			int cursorX = handler.getPlayer().getPlayerCursor().getbX();
			int cursorY = handler.getPlayer().getPlayerCursor().getbY();
			
			Block block = handler.getWorld().getBlock(cursorX, cursorY);
			
			// Check if there is a block
			if (block != null && block.getId() != BlockType.airBlock.getId()) {
				handler.getWorld().damageBlock(cursorX, cursorY, inventory.getSelectedToulbarItem());
			}
		}
		
	}
	
	public void selectedItemRightClick() {
		if (handler.getPlayer().getPlayerCursor().isCursorVisible()) {
			boolean consumeItems = inventory.getSelectedToulbarItem().rightClickAction(handler);
			if (consumeItems) {
				inventory.setSelectedToulbarItem(null);
			}
		}
		
	}
	
	// Add items to inventory
	public ArrayList<Item> addItemList(ArrayList<Item> newItems) {
		return inventory.addItemList(newItems);
	}
	
	// Check if the mouse is in the inventory or in any opened inventories
	public boolean isMouseInInventory() {
		int mX = handler.getMouseManager().getMouseX();
		int mY = handler.getMouseManager().getMouseY();
		
		if (inventory.isMouseInInventory(mX, mY, inventoryOpen)) {
			return true;
		} else if (inventoryOpen && craftingInventory.isMouseInInventory(mX, mY)) {
			return true;
		}
		
		switch (selectedInventory) {
			case CHEST:
				return chestSelected.getChestInventory().isMouseInInventory(mX, mY, true);
			case FURNACE:
				return furnaceSelected.isMouseInInventory(mX, mY);
			default:
				return false;
		}
	}
	
	public void toggleinventory() {
		inventoryOpen = !inventoryOpen;
		if (!inventoryOpen) {
			selectedInventory = OpenedInventoryEnum.NONE;
			chestSelected = null;
			furnaceSelected = null;
		}
	}
	
	public Item getToolbarItem() {
		return inventory.getToolbarItem();
	}
	
	public boolean hasSelectedToolbarItem() {
		return (inventory.getToolbarItem() != null);
	}
	
	public void setInventoryOpen(boolean open) {
		inventoryOpen = open;
		
		if (!open) {
			selectedInventory = OpenedInventoryEnum.NONE;
			chestSelected = null;
			furnaceSelected = null;
		}
	}
	
	public OpenedInventoryEnum getSelectedInventory() {
		return selectedInventory;
	}
	
	public void setSelectedInventory(OpenedInventoryEnum selectedInventory) {
		this.selectedInventory = selectedInventory;
	}
	
	public ChestTileEntity getChestSelected() {
		return chestSelected;
	}
	
	public void setChestSelected(ChestTileEntity chestSelected) {
		this.chestSelected = chestSelected;
	}
	
	public FurnaceTileEntity getFurnaceSelected() {
		return furnaceSelected;
	}
	
	public void setFurnaceSelected(FurnaceTileEntity furnaceSelected) {
		this.furnaceSelected = furnaceSelected;
	}
}
