package dev.sunslihgt.mine_game_2d.block.tile_entity;

import java.awt.Graphics;

import dev.sunslihgt.mine_game_2d.Handler;
import dev.sunslihgt.mine_game_2d.block.Block;
import dev.sunslihgt.mine_game_2d.block.BlockType;
import dev.sunslihgt.mine_game_2d.gfx.Assets;
import dev.sunslihgt.mine_game_2d.item.Item;
import dev.sunslihgt.mine_game_2d.item.ItemType;
import dev.sunslihgt.mine_game_2d.player.Inventory;
import dev.sunslihgt.mine_game_2d.player.PlayerInventory;
import dev.sunslihgt.mine_game_2d.player.PlayerInventory.OpenedInventoryEnum;
import dev.sunslihgt.mine_game_2d.recipes.CookingRecipes;

public class FurnaceTileEntity extends TileEntity {
	
	private final int INVENTORY_X_OFFSET = 600, INVENTORY_Y_OFFSET = 100;
	private final int INVENTORY_WIDTH = 9 * (Inventory.INVENTORY_SCREEN_CELL_SIZE + Inventory.INVENTORY_SCREEN_MARGIN) - Inventory.INVENTORY_SCREEN_MARGIN + Inventory.INVENTORY_SCREEN_BORDER * 2;
	private final int INVENTORY_HEIGHT = 3 * (Inventory.INVENTORY_SCREEN_CELL_SIZE + Inventory.INVENTORY_SCREEN_MARGIN) - Inventory.INVENTORY_SCREEN_MARGIN + Inventory.INVENTORY_SCREEN_BORDER * 2;
	private final int SMELTING_X_OFFSET = 760, SMELTED_X_OFFSET = 850, SMELTING_Y_OFFSET = 120;
	private final int FUEL_X_OFFSET = 760, FUEL_Y_OFFSET = 195;
	
	private final static int cookingStartAmount = 250;
	
	private Item fuelItem, cookingItem, cookedItem;
	
	private int cookingLeft = 0, fuelLeft = 0;
	
	private Handler handler;
	
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
		// Open or close furnace
		
		PlayerInventory playerInventory = handler.getPlayer().getPlayerInventory();
		
		if (playerInventory.getSelectedInventory() != OpenedInventoryEnum.FURNACE || playerInventory.getFurnaceSelected() != this) {
			playerInventory.setSelectedInventory(OpenedInventoryEnum.FURNACE);
			playerInventory.setChestSelected(null); // Remove player's chest
			playerInventory.setFurnaceSelected(this); // Set as player's furnace
			playerInventory.setInventoryOpen(true); // Open inventory
		} else { // Close inventory and chest
			playerInventory.setInventoryOpen(false); // Close inventory
		}
		
		return true; // Prevent from using an item's right click action
	}
	
	@Override
	public void render(Graphics g, int xOffset, int yOffset) {
		if (type.getTexture() != null) {
			// Texture
			if (fuelLeft > 0) { // Furnace On
				g.drawImage(Assets.furnace_block_on, x * BLOCK_WIDTH - xOffset, y * BLOCK_WIDTH - yOffset, BLOCK_WIDTH, BLOCK_WIDTH, null);
			} else { // Furnace Off
				g.drawImage(Assets.furnace_block_off, x * BLOCK_WIDTH - xOffset, y * BLOCK_WIDTH - yOffset, BLOCK_WIDTH, BLOCK_WIDTH, null);
			}
			
			// Breaking texture
			if (breakingStage > 0 && breakingStage - 1 < Assets.block_breaking.length) {
				g.drawImage(Assets.block_breaking[breakingStage - 1], x * BLOCK_WIDTH - xOffset, y * BLOCK_WIDTH - yOffset, BLOCK_WIDTH, BLOCK_WIDTH, null);
			}
		}
		
		// Lighting (darkness)
//		if (!type.background) {
//			int darknessRatio = (15 - Math.max(blockLight, skyLight)) * 17;
//			Color lightingColor = new Color(0, 0, 0, darknessRatio);
//			g.setColor(lightingColor);
//			g.fillRect(x * BLOCK_WIDTH - xOffset, y * BLOCK_WIDTH - yOffset, BLOCK_WIDTH, BLOCK_WIDTH);
//		}
	}
	
	// Called by the player if the furnace is opened
	public void renderFurnaceInventory(Graphics g) {
		// Background
		g.setColor(Inventory.INVENTORY_COLOR);
		g.fillRect(INVENTORY_X_OFFSET, INVENTORY_Y_OFFSET, INVENTORY_WIDTH, INVENTORY_HEIGHT);
		
		// Inventory cells
		g.setColor(Inventory.INVENTORY_CELL_COLOR);
		int cellSize = Inventory.INVENTORY_SCREEN_CELL_SIZE;
		g.fillRect(SMELTING_X_OFFSET, SMELTING_Y_OFFSET, cellSize, cellSize);
		g.fillRect(SMELTED_X_OFFSET, SMELTING_Y_OFFSET, cellSize, cellSize);
		g.fillRect(FUEL_X_OFFSET, FUEL_Y_OFFSET, cellSize, cellSize);
		
		int offset = (cellSize - Block.BLOCK_WIDTH) / 2;
		// Smelting item
		if (cookingItem != null) {
			Inventory.renderItem(g, SMELTING_X_OFFSET + offset, SMELTING_Y_OFFSET + offset, cookingItem, false);
		}
		// Smelted item
		if (cookedItem != null) {
			Inventory.renderItem(g, SMELTED_X_OFFSET + offset, SMELTING_Y_OFFSET + offset, cookedItem, false);
		}
		// Fuel item
		if (fuelItem != null) {
			Inventory.renderItem(g, FUEL_X_OFFSET + offset, FUEL_Y_OFFSET + offset, fuelItem, false);
		}
		
		// Render cooking arrow
		float cookingRatio = 1 - ((float) cookingLeft / (float) cookingStartAmount);
		g.setColor(Inventory.TOOLBAR_SELECTED_COLOR);
		g.fillRect(SMELTING_X_OFFSET + cellSize + 12, SMELTING_Y_OFFSET + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH);
		g.setColor(Inventory.INVENTORY_CELL_COLOR);
		g.fillRect(SMELTING_X_OFFSET + cellSize + 12, SMELTING_Y_OFFSET + offset, (int) (Block.BLOCK_WIDTH * cookingRatio), Block.BLOCK_WIDTH);
		
		g.drawImage(Assets.arrow, SMELTING_X_OFFSET + cellSize + 12, SMELTING_Y_OFFSET + offset, Block.BLOCK_WIDTH, Block.BLOCK_WIDTH, null);
	}
	
	public int getMouseHoveringSlot(int mX, int mY, boolean inventoryOpen) {
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
	
	public Item getItemWithIndex(int index) {
		if (index < 0 || index >= 3) {
			System.err.println("Index out of bounds in FurnaceTileEntity.getItemWithIndex(), index: " + index);
			return null;
		}
		
		switch (index) {
			case 0:
				return cookingItem;
			case 1:
				return cookedItem;
			case 2:
				return fuelItem;
			default:
				return null;
		}
	}
	
	public void setItemWithIndex(int index, Item item) {
		if (index < 0 || index >= 3) {
			System.err.println("Index out of bounds in FurnaceTileEntity.setItemWithIndex(), index: " + index);
		}
		
		switch (index) {
			case 0:
				cookingItem = item;
				break;
			case 1:
				cookedItem = item;
				break;
			case 2:
				fuelItem = item;
				break;
			default:
				break;
		}
	}
	
	public boolean isMouseInInventory(int mX, int mY) {
		if (mX >= INVENTORY_X_OFFSET && mX <= INVENTORY_X_OFFSET + INVENTORY_WIDTH && mY >= INVENTORY_Y_OFFSET && mY <= INVENTORY_Y_OFFSET + INVENTORY_HEIGHT) {
			return true;
		}
		return false;
	}
	
}
