package dev.sunslihgt.mine_game_2d.player;

import java.util.ArrayList;

import com.raylib.Raylib.KeyboardKey;
import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.block.TileEntity;
import dev.sunslihgt.mine_game_2d.block.tile_entities_list.ChestTileEntity;
import dev.sunslihgt.mine_game_2d.block.tile_entities_list.FurnaceTileEntity;
import dev.sunslihgt.mine_game_2d.gfx.Tooltip;
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
	public enum OpenedInventoryEnum {
		CHEST, FURNACE, NONE
	}
	
	private OpenedInventoryEnum selectedInventory = OpenedInventoryEnum.NONE;
	private ChestTileEntity chestSelected;
	private FurnaceTileEntity furnaceSelected;

	private final Handler handler;
	private final Player player;

	public PlayerInventory(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;

		init();
	}
	
	private void init() {
		inventory = new Inventory(9, 3, true, INVENTORY_X_OFFSET, INVENTORY_Y_OFFSET, TOOLBAR_Y_OFFSET);
		inventory.setItemWithIndex(0, new Item(1, ItemType.woodenPickaxeItem));
		inventory.setItemWithIndex(1, new Item(1, ItemType.stonePickaxeItem));
		inventory.setItemWithIndex(2, new Item(1, ItemType.ironPickaxeItem));
		inventory.setItemWithIndex(3, new Item(12, ItemType.torchItem));
		inventory.setItemWithIndex(4, new Item(48, ItemType.redTorchItem));
		inventory.setItemWithIndex(5, new Item(64, ItemType.blueTorchItem));
		inventory.setItemWithIndex(6, new Item(16, ItemType.greenTorchItem));
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

		inventory.setItemWithIndex(26, new Item(12, ItemType.stoneItem));
		inventory.setItemWithIndex(27, new Item(48, ItemType.dirtItem));
		inventory.setItemWithIndex(28, new Item(64, ItemType.coalItem));
		
		craftingInventory = new CraftingInventory();
	}
	
	public void tick() {
		updateAvailableCrafts();

		checkMouseInput();

		updateAvailableCrafts(); // Update again, in case the inventories have changed, before rendering
	}

	/**
	 * Manage mouse inputs in player inventories (player inventory, toolbar, crafting interface and containers)
	 */
	public void checkMouseInput() {
		// Mouse Wheel
		int scroll = handler.getMouseManager().getScroll();
		if (scroll != 0) {
			inventory.moveToolbarIndex(scroll);
		}

		// Inventory
		handleInventoriesMouseActions();
	}

	private void handleInventoriesMouseActions() {
		int mX = handler.getMouseManager().getMouseX();
		int mY = handler.getMouseManager().getMouseY();

		// Handle main inventory
		boolean mouseHoverUI = handleContainerAction(inventory, getSelectedInventoryContainer(), mX, mY);

		// Handle crafting inventory
		if (!mouseHoverUI) {
			mouseHoverUI = handleCraftingInventoryAction(mX, mY);
		}

		// Handle selected inventory
		if (!mouseHoverUI) {
			ISlotContainer selectedInventoryContainer = getSelectedInventoryContainer();
			if (selectedInventoryContainer != null) {
                handleContainerAction(selectedInventoryContainer, inventory, mX, mY);
            }
		}
	}

	/**
	 * Handle the mouse actions for a given container.
	 * @param container An {@link ISlotContainer} instance
	 *
	 * @param mX Mouse x position
	 * @param mY Mouse y position
	 * @return true if the mouse is hovering the container UI.
	 */
	private boolean handleContainerAction(ISlotContainer container, ISlotContainer alternativeContainer, int mX, int mY) {
		if (container == null) {
			System.err.println();
			return false;
		}

		boolean transferKeyDown = handler.getKeyboardManager().keyDown(KeyboardKey.KEY_LEFT_SHIFT);
		boolean mouseHoveringContainer = container.isMouseHoveringUI(mX, mY, inventoryOpen);

		if (mouseHoveringContainer) {
			int slot = container.getMouseSlotIndex(mX, mY, inventoryOpen);
			if (slot >= 0 && slot < container.getInventoryCellsAmount()) { // Mouse hovering slot
				Item item = container.getItemWithIndex(slot);
				if (handler.getMouseManager().isLeftJustPressed()) { // Left click
					if (transferKeyDown) {
						if (alternativeContainer != null) {
							item = alternativeContainer.addItem(item);
							if (item == null || item.getCount() == 0) {
								container.setItemWithIndex(slot, null);
							}
						} else if (container instanceof Inventory containerInventory) { // No other container open and instance of Inventory
                            if (containerInventory.getHasToolbar()) { // Has a toolbar -> transfer item toolbar <> inventory
								containerInventory.transferSlotToolbarInventory(slot);
							}
						}
					} else if (cursorSelectedItem == null) { // No selected item -> select new
						if (item != null) {
							cursorSelectedItem = item;
							container.setItemWithIndex(slot, null);
						}
					} else { // Cursor carrying an item + a new slot is clicked
						// Move item
						if (item == null) {
							container.setItemWithIndex(slot, cursorSelectedItem);
							cursorSelectedItem = null;
						} else if (cursorSelectedItem.getId() == item.getId()) { // Combine items
							int inventoryItemCount = Math.min(item.getCount() + cursorSelectedItem.getCount(), cursorSelectedItem.getType().getMaxStack());
							int cursorItemCount = item.getCount() + cursorSelectedItem.getCount() - inventoryItemCount;
							item.setCount(inventoryItemCount);
							if (cursorItemCount <= 0) { // No more cursor item -> reset
								cursorSelectedItem = null;
							} else {
								cursorSelectedItem.setCount(cursorItemCount);
							}
						} else { // Different items -> Swap items
							Item cursorItemBuffer = cursorSelectedItem;
							cursorSelectedItem = item;
							container.setItemWithIndex(slot, cursorItemBuffer);
						}
					}
				} else if (handler.getMouseManager().isRightJustPressed()) { // Right click
					if (cursorSelectedItem == null) { // No selected item -> select half of item stack
						if (item != null) {
							int itemCountToLeave = item.getCount() / 2; // Half of the item count is left in place
							int cursorItemCount = item.getCount() - itemCountToLeave; // The rest is taken by the cursor
							cursorSelectedItem = new Item(cursorItemCount, item.getType());
							if (itemCountToLeave <= 0) {
								container.setItemWithIndex(slot, null);
							} else {
								item.setCount(itemCountToLeave);
							}
						}
					} else { // Cursor selected item -> select a 2nd slot
						if (item == null) { // Deposit one item in empty slot
							container.setItemWithIndex(slot, new Item(1, cursorSelectedItem.getType()));

							if (cursorSelectedItem.getCount() - 1 <= 0) {
								cursorSelectedItem = null;
							} else {
								cursorSelectedItem.addCount(-1);
							}
						} else if (item.getId() == cursorSelectedItem.getId()) { // Deposit one item in slot
							if (item.getCount() + 1 <= item.getType().getMaxStack()) { // slot not full
								item.addCount(1);
								if (cursorSelectedItem.getCount() - 1 <= 0) {
									cursorSelectedItem = null;
								} else {
									cursorSelectedItem.addCount(-1);
								}
							}
						} else { // Different items -> Swap items
							Item cursorItemBuffer = cursorSelectedItem;
							cursorSelectedItem = item;
							container.setItemWithIndex(slot, cursorItemBuffer);
						}
					}
				}
			}
		}

		return mouseHoveringContainer;
	}

	private boolean handleCraftingInventoryAction(int mX, int mY) {
		if (!inventoryOpen) return false;

		boolean mouseHoveringCraftingInventory = craftingInventory.isMouseHoveringUI(mX, mY);
		if (mouseHoveringCraftingInventory) {
			if (handler.getMouseManager().isLeftJustPressed() && !craftingInventory.clickCategory(mX, mY)) {
				CraftingRecipe craft = craftingInventory.getMouseHoveringCraft(mX, mY);
				if (craft != null) {
					Item craftedItem = craft.craftedItem().getCopy();
					if (cursorSelectedItem == null || (cursorSelectedItem.getId() == craftedItem.getId() && cursorSelectedItem.getCount() + craftedItem.getCount() <= craftedItem.getType().getMaxStack())) {
						ISlotContainer secondaryContainer = getSelectedInventoryContainer();
						if (craftingInventory.consumeCraftItems(craft, inventory, secondaryContainer)) {
							// Craft successful
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

		return mouseHoveringCraftingInventory;
	}

	private void updateAvailableCrafts() {
		ArrayList<Item> availableItems = inventory.getItemsListCopy();
		ISlotContainer container = getSelectedInventoryContainer();
		if (container != null) {
			availableItems = Inventory.combineItemsLists(availableItems, container.getItemsListCopy());
		}
		craftingInventory.updateAvailableCrafts(availableItems);
	}
	
	public void render() {
		// Render inventory and toolbar
		inventory.render(inventoryOpen);
		
		if (inventoryOpen) {
			// Render crafting inventory
			craftingInventory.render();

			// Render container
			if (selectedInventory == OpenedInventoryEnum.CHEST && chestSelected != null) {
				chestSelected.renderChestInventory();
			} else if (selectedInventory == OpenedInventoryEnum.FURNACE && furnaceSelected != null) {
				furnaceSelected.renderFurnaceInventory();
			}
		}
		
		// Render cursor item
		int mX = handler.getMouseManager().getMouseX();
		int mY = handler.getMouseManager().getMouseY();
		ISlotContainer container = getSelectedInventoryContainer();
		if (cursorSelectedItem != null) {
			Inventory.renderItem(mX, mY, cursorSelectedItem, true);
		} else {
			Item tooltipItem = null;

			if (inventoryOpen) {
				// Inventory
				tooltipItem = inventory.getMouseSlotItem(mX, mY, inventoryOpen);

				// Chest inventory
				if (tooltipItem == null && container != null) tooltipItem = container.getMouseSlotItem(mX, mY, inventoryOpen);

				// Crafting inventory
				if (tooltipItem == null && craftingInventory.isMouseHoveringUI(mX, mY)) {
					CraftingRecipe craft = craftingInventory.getMouseHoveringCraft(mX, mY);
					if (craft != null) {
						tooltipItem = craft.craftedItem();
					}
				}
			}

			if (tooltipItem != null) {
				Tooltip.drawToolTip(mX, mY, tooltipItem);
			}
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
				switch (player.getGameMode()) {
                    case SURVIVAL -> handler.getWorld().damageBlock(cursorX, cursorY, inventory.getSelectedToolbarItem());
                    case CREATIVE, SPECTATOR -> handler.getWorld().deleteBlock(cursorX, cursorY);
                }
			}
		}
		
	}
	
	public void selectedItemRightClick() {
		if (handler.getPlayer().getPlayerCursor().isCursorVisible()) {
			boolean consumeItems = inventory.getSelectedToolbarItem().rightClickAction(handler);
			if (consumeItems) {
				inventory.setSelectedToolbarItem(null);
			}
		}
	}
	
	// Add items to inventory
	public ArrayList<Item> addItemList(ArrayList<Item> newItems) {
		return inventory.addItemList(newItems);
	}
	
	// Check if the mouse is in the inventory or in any opened inventories
	public boolean isMouseInAnyInventory() {
		int mX = handler.getMouseManager().getMouseX();
		int mY = handler.getMouseManager().getMouseY();

		if (inventory.isMouseHoveringUI(mX, mY, inventoryOpen)) {
			return true;
		}

		if (inventoryOpen) {
			if (craftingInventory.isMouseHoveringUI(mX, mY)) {
				return true;
			}

			ISlotContainer selectedInventoryContainer = getSelectedInventoryContainer();
			if (selectedInventoryContainer != null) {
				return selectedInventoryContainer.isMouseHoveringUI(mX, mY, inventoryOpen);
			}
		}

		return false;
	}
	
	public void toggleInventory() {
		setInventoryOpen(!inventoryOpen);
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

			if (cursorSelectedItem != null) { // Item selected
				// Transfer or drop item

				// Add items to inventory and store remaining items
				Item itemsLeft = inventory.addItem(cursorSelectedItem);
				cursorSelectedItem = null;

				// TODO: itemsLeft should be dropped
				System.err.println("Not enough space in inventory to store the items, cursor item was deleted");
			}
		}
	}
	
	public OpenedInventoryEnum getSelectedInventory() {
		return selectedInventory;
	}
	
	public void setSelectedInventory(OpenedInventoryEnum newSelectedInventory, TileEntity tileEntity) {
		if (newSelectedInventory == OpenedInventoryEnum.CHEST && tileEntity instanceof ChestTileEntity chestTileEntity) {
			chestSelected = chestTileEntity;
			furnaceSelected = null;
		} else if (newSelectedInventory == OpenedInventoryEnum.FURNACE && tileEntity instanceof FurnaceTileEntity furnaceTileEntity) {
			chestSelected = null;
			furnaceSelected = furnaceTileEntity;
		} else if (newSelectedInventory == OpenedInventoryEnum.NONE && tileEntity == null) {
			chestSelected = null;
			furnaceSelected = null;
		} else {
			System.err.println("PlayerInvetory.setSelectedInventory: Invalid OpenedInventoryEnum=" + newSelectedInventory);
			return;
		}

		selectedInventory = newSelectedInventory;
	}

	public ISlotContainer getSelectedInventoryContainer() {
		return switch (selectedInventory) {
			case CHEST -> chestSelected.getChestInventory();
			case FURNACE -> furnaceSelected;
			default -> null;
		};
	}
	
	public ChestTileEntity getChestSelected() {
		return chestSelected;
	}

	public FurnaceTileEntity getFurnaceSelected() {
		return furnaceSelected;
	}
}
