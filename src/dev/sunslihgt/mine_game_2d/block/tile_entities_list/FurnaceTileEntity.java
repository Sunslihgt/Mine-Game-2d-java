package dev.sunslihgt.mine_game_2d.block.tile_entities_list;

import com.raylib.Raylib;
import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.block.BlockDrop;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.block.TileEntity;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.player.ISlotContainer;
import dev.sunslihgt.mine_game_2d.player.Inventory;
import dev.sunslihgt.mine_game_2d.player.PlayerInventory;
import dev.sunslihgt.mine_game_2d.player.PlayerInventory.OpenedInventoryEnum;
import dev.sunslihgt.mine_game_2d.recipes.CookingRecipes;
import dev.sunslihgt.mine_game_2d.utils.RaylibUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class FurnaceTileEntity extends TileEntity implements ISlotContainer {

	// UI constants
	private final int INVENTORY_X_OFFSET = 600, INVENTORY_Y_OFFSET = 100;
	private final int INVENTORY_WIDTH = 9 * (Inventory.INVENTORY_SCREEN_CELL_SIZE + Inventory.INVENTORY_SCREEN_MARGIN) - Inventory.INVENTORY_SCREEN_MARGIN + Inventory.INVENTORY_SCREEN_BORDER * 2;
	private final int INVENTORY_HEIGHT = 3 * (Inventory.INVENTORY_SCREEN_CELL_SIZE + Inventory.INVENTORY_SCREEN_MARGIN) - Inventory.INVENTORY_SCREEN_MARGIN + Inventory.INVENTORY_SCREEN_BORDER * 2;
	private final int SMELTING_X_OFFSET = 760, SMELTED_X_OFFSET = 850, SMELTING_Y_OFFSET = 120;
	private final int FUEL_X_OFFSET = 760, FUEL_Y_OFFSET = 195;

	// Inventory fields
	private final static int cookingStartAmount = 250;

	private Item fuelItem, cookingItem, cookedItem;
	private final static int COOKING_SLOT = 0, COOKED_SLOT = 1, FUEL_SLOT = 2;
	private final static int CELL_COUNT = 3;

	private int cookingLeft = 0, fuelLeft = 0;
	
	private final Handler handler;
	
	public FurnaceTileEntity(int x, int y, Handler handler) {
		super(x, y, BlockType.furnaceBlock);
		
		this.handler = handler;
	}
	
	// Cook items using fuel
	@Override
	public void tick() {
		// Check if there is something is cooking
		fuelLeft = Math.max(0, fuelLeft - 1);
		
//		System.out.println("fuel left: " + fuelLeft + ", cooking left: " + cookingLeft);
		
		if (fuelLeft > 0) { // Some fuel left
			if (cookingLeft > 0) {
				if (cookingItem == null || !CookingRecipes.isCookable(cookingItem.getType())) {
					// No item to cook
					cookingLeft = 0;
				} else if (cookedItem != null && cookedItem.getType() != CookingRecipes.getCookedItemType(cookingItem.getType())) {
					// Not enough room to cook (different cooked item)
					cookingLeft = 0;
				} else if (cookedItem != null && cookedItem.getType() == CookingRecipes.getCookedItemType(cookingItem.getType()) && cookedItem.getCount() + CookingRecipes.getCookedItemCount(cookingItem.getType()) > cookedItem.getType().getMaxStack()) {
					// Not enough room to cook (max cooked items reached)
					cookingLeft = 0;
				} else {
					cookingLeft -= 1;
					if (cookingLeft <= 0) {
						// Finished cooking -> remove cooking item and add cooked item
						// Add cooked item
						ItemType cookedItemType = CookingRecipes.getCookedItemType(cookingItem.getType());
						if (cookedItem == null) {
							// No current cooked item -> create new
							cookedItem = new Item(CookingRecipes.getCookedItemCount(cookingItem.getType()), cookedItemType);
						} else if (cookedItem.getType() != cookedItemType || cookedItem.getCount() + CookingRecipes.getCookedItemCount(cookingItem.getType()) > cookedItem.getType().getMaxStack()) {
							// No room to cook item -> error
							System.err.println("No room to cook item");
						} else {
							cookedItem.addCount(CookingRecipes.getCookedItemCount(cookingItem.getType()));
						}
						// Remove cooking item
						if (cookingItem.getCount() - 1 <= 0) {
							cookingItem = null;
						} else {
							cookingItem.addCount(-1);
						}
					}
				}
			} else { // No item cooking but some fuel left
				if (cookingItem == null || !CookingRecipes.isCookable(cookingItem.getType())) {
					// No item to cook
					cookingLeft = 0;
				} else if (cookedItem != null && cookedItem.getType() != CookingRecipes.getCookedItemType(cookingItem.getType())) {
					// Not enough room to cook (different cooked item)
					cookingLeft = 0;
				} else if (cookedItem != null && cookedItem.getType() == CookingRecipes.getCookedItemType(cookingItem.getType()) && cookedItem.getCount() + CookingRecipes.getCookedItemCount(cookingItem.getType()) > cookedItem.getType().getMaxStack()) {
					// Not enough room to cook (max cooked items reached)
					cookingLeft = 0;
				} else {
					// There is an item to cook
					cookingLeft = cookingStartAmount - 1;
				}
			}
		} else { // No more fuel
			if (cookingItem == null || !CookingRecipes.isCookable(cookingItem.getType())) {
				// No item to cook
				cookingLeft = 0;
			} else if (cookedItem != null && cookedItem.getType() != CookingRecipes.getCookedItemType(cookingItem.getType())) {
				// Not enough room to cook (different cooked item)
				cookingLeft = 0;
			} else if (cookedItem != null && cookedItem.getType() == CookingRecipes.getCookedItemType(cookingItem.getType()) && cookedItem.getCount() + CookingRecipes.getCookedItemCount(cookingItem.getType()) > cookedItem.getType().getMaxStack()) {
				// Not enough room to cook (max cooked items reached)
				cookingLeft = 0;
			} else {
				// There is an item to cook
				if (fuelItem != null && fuelItem.getType().getFuelBurnTime() > 0) {
					// Use fuel
					fuelLeft = (int) (fuelItem.getType().getFuelBurnTime() * cookingStartAmount);
					if (cookingLeft <= 0) {
						cookingLeft = cookingStartAmount - 1;
					}
					// Remove fuel item
					if (fuelItem.getCount() - 1 > 0) {
						fuelItem.addCount(-1);
					} else {
						fuelItem = null;
					}
				} else {
					// Stop cooking
					cookingLeft = 0;
				}
			}
		}
	}
	
	@Override
	public boolean rightClickBlock() {
		// Open or close furnace on mouse just pressed
		if (!handler.getMouseManager().isRightJustPressed()) return false;

		PlayerInventory playerInventory = handler.getPlayer().getPlayerInventory();

		if (playerInventory.getSelectedInventory() != OpenedInventoryEnum.FURNACE || playerInventory.getFurnaceSelected() != this) {
			playerInventory.setInventoryOpen(true); // Open inventory
			playerInventory.setSelectedInventory(OpenedInventoryEnum.FURNACE, this);
		} else { // Close furnace
			playerInventory.setSelectedInventory(OpenedInventoryEnum.NONE, null);
		}
		
		return true; // Prevent from using an item's right click action
	}

	@Override
	public void clearInventory() {
		cookingItem = null;
		cookedItem = null;
		fuelItem = null;
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
		drops.addAll(getItemsListCopy());
		clearInventory();

		return drops;
	}
	
	@Override
	public void render(int xOffset, int yOffset) {
		if (type.getTexture() != null) {
			// Texture
			if (fuelLeft > 0) { // Furnace On
				RaylibUtils.draw(Assets.furnace_block_on, x * BLOCK_WIDTH - xOffset, y * BLOCK_WIDTH - yOffset, BLOCK_WIDTH, BLOCK_WIDTH);
			} else { // Furnace Off
				RaylibUtils.draw(Assets.furnace_block_off, x * BLOCK_WIDTH - xOffset, y * BLOCK_WIDTH - yOffset, BLOCK_WIDTH, BLOCK_WIDTH);
			}
			
			// Breaking texture
			if (breakingStage > 0 && breakingStage - 1 < Assets.block_breaking.length) {
				RaylibUtils.draw(Assets.block_breaking[breakingStage - 1], x * BLOCK_WIDTH - xOffset, y * BLOCK_WIDTH - yOffset, BLOCK_WIDTH, BLOCK_WIDTH);
			}
		}
		
		// TODO: change emitted light when cooking
		// Lighting (darkness)
//		if (!type.background) {
//			int darknessRatio = (15 - Math.max(blockLight, skyLight)) * 17;
//			Color lightingColor = new Color(0, 0, 0, darknessRatio);
//			g.setColor(lightingColor);
//			g.fillRect(x * BLOCK_WIDTH - xOffset, y * BLOCK_WIDTH - yOffset, BLOCK_WIDTH, BLOCK_WIDTH);
//		}
	}
	
	// Called by the player if the furnace is opened
	public void renderFurnaceInventory() {
		// Background
		Raylib.drawRectangle(INVENTORY_X_OFFSET, INVENTORY_Y_OFFSET, INVENTORY_WIDTH, INVENTORY_HEIGHT, Inventory.INVENTORY_COLOR);
		
		// Inventory cells
		int cellSize = Inventory.INVENTORY_SCREEN_CELL_SIZE;
		Raylib.drawRectangle(SMELTING_X_OFFSET, SMELTING_Y_OFFSET, cellSize, cellSize, Inventory.INVENTORY_CELL_COLOR);
		Raylib.drawRectangle(SMELTED_X_OFFSET, SMELTING_Y_OFFSET, cellSize, cellSize, Inventory.INVENTORY_CELL_COLOR);
		Raylib.drawRectangle(FUEL_X_OFFSET, FUEL_Y_OFFSET, cellSize, cellSize, Inventory.INVENTORY_CELL_COLOR);
		
		int offset = (cellSize - Block.BLOCK_WIDTH) / 2;
		// Smelting item
		if (cookingItem != null) {
			Inventory.renderItem(SMELTING_X_OFFSET + offset, SMELTING_Y_OFFSET + offset, cookingItem, false);
		}
		// Smelted item
		if (cookedItem != null) {
			Inventory.renderItem(SMELTED_X_OFFSET + offset, SMELTING_Y_OFFSET + offset, cookedItem, false);
		}
		// Fuel item
		if (fuelItem != null) {
			Inventory.renderItem(FUEL_X_OFFSET + offset, FUEL_Y_OFFSET + offset, fuelItem, false);
		}
		
		// Render cooking arrow
		float cookingRatio = 1 - ((float) cookingLeft / (float) cookingStartAmount);
		Raylib.drawRectangle(SMELTING_X_OFFSET + cellSize + 12, SMELTING_Y_OFFSET + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH, Inventory.TOOLBAR_SELECTED_COLOR);
		Raylib.drawRectangle(SMELTING_X_OFFSET + cellSize + 12, SMELTING_Y_OFFSET + offset, (int) (Block.BLOCK_WIDTH * cookingRatio), Block.BLOCK_WIDTH, Inventory.INVENTORY_CELL_COLOR);
		
		RaylibUtils.draw(Assets.arrow, SMELTING_X_OFFSET + cellSize + 12, SMELTING_Y_OFFSET + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH);
	}

	@Override
	public int getMouseSlotIndex(int mX, int mY, boolean inventoryOpen) {
		if (mY >= SMELTING_Y_OFFSET && mY <= SMELTING_Y_OFFSET + Inventory.INVENTORY_SCREEN_CELL_SIZE) {
			if (mX >= SMELTING_X_OFFSET && mX <= SMELTING_X_OFFSET + Inventory.INVENTORY_SCREEN_CELL_SIZE) {
				return 0;
			} else if (mX >= SMELTED_X_OFFSET && mX <= SMELTED_X_OFFSET + Inventory.INVENTORY_SCREEN_CELL_SIZE) {
				return 1;
			}
		} else if (mX >= FUEL_X_OFFSET && mX <= FUEL_X_OFFSET + Inventory.INVENTORY_SCREEN_CELL_SIZE && mY >= FUEL_Y_OFFSET && mY <= FUEL_Y_OFFSET + Inventory.INVENTORY_SCREEN_CELL_SIZE) {
			return 2;
		}
		
		return -1;
	}

	@Override
	public Item getMouseSlotItem(int mX, int mY, boolean inventoryOpen) {
		int slot = getMouseSlotIndex(mX, mY, inventoryOpen);
		if (slot >= 0 && slot < getInventoryCellsAmount()) return getItemWithIndex(slot);
		return null;
	}

	/**
	 * Add items to inventory, according to the inventory's preferences.
	 * Furnace-specific: do not fill output + only fill fuel with burnable items.
	 * @return The remaining items
	 */
	@Override
	public Item addItem(Item newItem) {
		int slotToFill = COOKING_SLOT;
		if (newItem.getType().getFuelBurnTime() > 0) {
			slotToFill = FUEL_SLOT; // Fill fuel slot
		} else if (!CookingRecipes.isCookable(newItem.getType())) {
			return newItem; // Not fuel + not cookable -> exit
		}

		Item slotItem = getItemWithIndex(slotToFill);
		if (slotItem == null) {
			setItemWithIndex(slotToFill, newItem);
			return null;
		} else if (slotItem.getId() == newItem.getId()) {
			int amountTransferred = Math.min(newItem.getCount(), slotItem.getType().getMaxStack() - slotItem.getCount());
			slotItem.addCount(amountTransferred);
			if (amountTransferred < newItem.getCount()) {
				newItem.addCount(-amountTransferred);
				return newItem; // Items remaining
			} else {
				return null;
			}
		} else { // Different item, do nothing
			return newItem; // Items remaining
		}
	}

	/**
	 * Add a list of items to inventory, according to the inventory's preferences.
	 * Furnace-specific: do not fill output + only fill fuel with burnable items.
	 * @return The list of remaining items
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

	@Override
	public Item getItemWithIndex(int index) {
		if (index < 0 || index >= CELL_COUNT) {
			System.err.println("Index out of bounds in FurnaceTileEntity.getItemWithIndex(), index: " + index);
			return null;
		}

        return switch (index) {
			case COOKING_SLOT -> cookingItem;
			case COOKED_SLOT -> cookedItem;
			case FUEL_SLOT -> fuelItem;
            default -> null;
        };
	}

	@Override
	public void setItemWithIndex(int index, Item item) {
		if (index < 0 || index >= CELL_COUNT) {
			System.err.println("Index out of bounds in FurnaceTileEntity.setItemWithIndex(), index: " + index);
		}

        switch (index) {
            case COOKING_SLOT -> cookingItem = item;
            case COOKED_SLOT -> cookedItem = item;
            case FUEL_SLOT -> fuelItem = item;
        }
	}

	@Override
	public void setItemCountWithIndex(int index, int count) {
		if (index < 0 || index >= CELL_COUNT) {
			System.err.println("Index out of bounds in FurnaceTileEntity.setItemWithIndex(), index: " + index);
		}

        switch (index) {
            case COOKING_SLOT -> cookingItem.setCount(count);
            case COOKED_SLOT -> cookedItem.setCount(count);
            case FUEL_SLOT -> fuelItem.setCount(count);
        }
	}

	// Return the number of items of a chosen id
	@Override
	public int countItemId(int id) {
		int count = 0;
		if (cookingItem != null && cookingItem.getId() == id) count += cookingItem.getCount();
		if (cookedItem != null && cookedItem.getId() == id) count += cookedItem.getCount();
		if (fuelItem != null && fuelItem.getId() == id) count += fuelItem.getCount();
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

		for (int slot = 0; slot < getInventoryCellsAmount(); slot++) {
			Item inventoryItem = getItemWithIndex(slot);
			if (inventoryItem != null && inventoryItem.getId() == item.getId()) {
				int itemsRemovedCount = Math.min(itemsToRemove, inventoryItem.getCount());
				if (inventoryItem.getCount() - itemsRemovedCount <= 0) {
					setItemWithIndex(slot, null);
				} else {
					inventoryItem.addCount(-itemsRemovedCount);
				}
				itemsToRemove -= itemsRemovedCount;
			}
			if (itemsToRemove <= 0) {
				return true;
			}
		}

		System.err.println("Not enough items in inventory while removing in Inventory.removeItem()");
		return false;
	}

	@Override
	public int getInventoryCellsAmount() {
		return CELL_COUNT;
	}

	@Override
	public boolean isMouseHoveringUI(int mX, int mY, boolean inventoryOpen) {
		if (!inventoryOpen) return false;
		if (mX >= INVENTORY_X_OFFSET && mX <= INVENTORY_X_OFFSET + INVENTORY_WIDTH && mY >= INVENTORY_Y_OFFSET && mY <= INVENTORY_Y_OFFSET + INVENTORY_HEIGHT) {
			return true;
		}
		return false;
	}

	/**
	 * Get a list containing all the items in inventory without duplicates.<br>
	 * WARNING ! Items may have a higher count than max stack count !
	 */
	@Override
	public ArrayList<Item> getItemsListCopy() {
		ArrayList<Item> itemsList = new ArrayList<Item>();

		ArrayList<Item> items = new ArrayList<>();
		if (cookingItem != null) items.add(cookingItem);
		if (cookedItem != null) items.add(cookedItem);
		if (fuelItem != null) items.add(fuelItem);

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
}
